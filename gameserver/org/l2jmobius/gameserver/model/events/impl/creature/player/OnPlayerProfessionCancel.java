package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author Krunchy
 * @since 2.6.0.0
 */
public class OnPlayerProfessionCancel implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _classId;
	
	public OnPlayerProfessionCancel(PlayerInstance player, int classId)
	{
		_player = player;
		_classId = classId;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getClassId()
	{
		return _classId;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_PROFESSION_CANCEL;
	}
}