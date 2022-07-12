package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.Playable;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayableExpChanged implements IBaseEvent
{
	private final Playable _playable;
	private final long _oldExp;
	private final long _newExp;
	
	public OnPlayableExpChanged(Playable playable, long oldExp, long newExp)
	{
		_playable = playable;
		_oldExp = oldExp;
		_newExp = newExp;
	}
	
	public Playable getPlayable()
	{
		return _playable;
	}
	
	public long getOldExp()
	{
		return _oldExp;
	}
	
	public long getNewExp()
	{
		return _newExp;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYABLE_EXP_CHANGED;
	}
}
