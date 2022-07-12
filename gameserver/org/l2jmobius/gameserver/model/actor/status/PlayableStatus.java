package org.l2jmobius.gameserver.model.actor.status;

import org.l2jmobius.gameserver.model.actor.Playable;

public class PlayableStatus extends CreatureStatus
{
	public PlayableStatus(Playable activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public Playable getActiveChar()
	{
		return (Playable) super.getActiveChar();
	}
}
