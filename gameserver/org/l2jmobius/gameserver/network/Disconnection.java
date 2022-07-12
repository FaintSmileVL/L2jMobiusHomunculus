package org.l2jmobius.gameserver.network;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.instancemanager.AntiFeedManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerLogout;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;
import org.l2jmobius.gameserver.taskmanager.AttackStanceTaskManager;

/**
 * @author NB4L1
 */
public class Disconnection
{
	private static final Logger LOGGER = Logger.getLogger(Disconnection.class.getName());
	
	public static GameClient getClient(GameClient client, PlayerInstance player)
	{
		if (client != null)
		{
			return client;
		}
		
		if (player != null)
		{
			return player.getClient();
		}
		
		return null;
	}
	
	public static PlayerInstance getActiveChar(GameClient client, PlayerInstance player)
	{
		if (player != null)
		{
			return player;
		}
		
		if (client != null)
		{
			return client.getPlayer();
		}
		
		return null;
	}
	
	private final GameClient _client;
	private final PlayerInstance _player;
	
	private Disconnection(GameClient client)
	{
		this(client, null);
	}
	
	public static Disconnection of(GameClient client)
	{
		return new Disconnection(client);
	}
	
	private Disconnection(PlayerInstance player)
	{
		this(null, player);
	}
	
	public static Disconnection of(PlayerInstance player)
	{
		return new Disconnection(player);
	}
	
	private Disconnection(GameClient client, PlayerInstance player)
	{
		_client = getClient(client, player);
		_player = getActiveChar(client, player);
		
		// Stop player tasks.
		if (_player != null)
		{
			_player.stopAllTasks();
		}
		
		// Anti Feed
		AntiFeedManager.getInstance().onDisconnect(_client);
		
		if (_client != null)
		{
			_client.setPlayer(null);
		}
		
		if (_player != null)
		{
			_player.setClient(null);
		}
	}
	
	public static Disconnection of(GameClient client, PlayerInstance player)
	{
		return new Disconnection(client, player);
	}
	
	public Disconnection storeMe()
	{
		try
		{
			if (_player != null)
			{
				_player.storeMe();
			}
		}
		catch (RuntimeException e)
		{
			LOGGER.warning(e.getMessage());
		}
		return this;
	}
	
	public Disconnection deleteMe()
	{
		try
		{
			if ((_player != null) && _player.isOnline())
			{
				EventDispatcher.getInstance().notifyEventAsync(new OnPlayerLogout(_player), _player);
				_player.deleteMe();
			}
		}
		catch (RuntimeException e)
		{
			LOGGER.warning(e.getMessage());
		}
		return this;
	}
	
	public Disconnection close(boolean toLoginScreen)
	{
		if (_client != null)
		{
			_client.close(toLoginScreen);
		}
		return this;
	}
	
	public Disconnection close(IClientOutgoingPacket packet)
	{
		if (_client != null)
		{
			_client.close(packet);
		}
		return this;
	}
	
	public void defaultSequence(boolean toLoginScreen)
	{
		defaultSequence();
		close(toLoginScreen);
	}
	
	public void defaultSequence(IClientOutgoingPacket packet)
	{
		defaultSequence();
		close(packet);
	}
	
	private void defaultSequence()
	{
		storeMe();
		deleteMe();
	}
	
	public void onDisconnection()
	{
		if (_player != null)
		{
			ThreadPool.schedule(this::defaultSequence, _player.canLogout() ? 0 : AttackStanceTaskManager.COMBAT_TIME);
		}
	}
}