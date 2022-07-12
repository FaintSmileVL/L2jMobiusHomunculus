package org.l2jmobius.gameserver.model.events.impl.server;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author UnAfraid
 */
public class OnPacketReceived implements IBaseEvent
{
	private final GameClient _client;
	private final byte[] _data;
	
	public OnPacketReceived(GameClient client, byte[] data)
	{
		_client = client;
		_data = data;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _client.getPlayer();
	}
	
	public GameClient getClient()
	{
		return _client;
	}
	
	public byte[] getData()
	{
		return _data;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PACKET_RECEIVED;
	}
}