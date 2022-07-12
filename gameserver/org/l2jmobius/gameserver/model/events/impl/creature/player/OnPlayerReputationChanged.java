package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerReputationChanged implements IBaseEvent
{
	private final PlayerInstance _player;
	private final int _oldReputation;
	private final int _newReputation;
	
	public OnPlayerReputationChanged(PlayerInstance player, int oldReputation, int newReputation)
	{
		_player = player;
		_oldReputation = oldReputation;
		_newReputation = newReputation;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public int getOldReputation()
	{
		return _oldReputation;
	}
	
	public int getNewReputation()
	{
		return _newReputation;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_REPUTATION_CHANGED;
	}
}
