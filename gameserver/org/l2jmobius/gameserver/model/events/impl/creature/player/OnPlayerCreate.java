package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author UnAfraid
 */
public class OnPlayerCreate implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _objectId;
	private final String _name;
	private final GameClient _client;
	
	public OnPlayerCreate(PlayerInstance player, int objectId, String name, GameClient client)
	{
		_player = player;
		_objectId = objectId;
		_name = name;
		_client = client;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getObjectId()
	{
		return _objectId;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public GameClient getClient()
	{
		return _client;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_CREATE;
	}
}
