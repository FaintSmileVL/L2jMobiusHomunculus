package org.l2jmobius.gameserver.model.actor.status;

import org.l2jmobius.gameserver.model.actor.instance.StaticObjectInstance;

public class StaticObjectStatus extends CreatureStatus
{
	public StaticObjectStatus(StaticObjectInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public StaticObjectInstance getActiveChar()
	{
		return (StaticObjectInstance) super.getActiveChar();
	}
}
