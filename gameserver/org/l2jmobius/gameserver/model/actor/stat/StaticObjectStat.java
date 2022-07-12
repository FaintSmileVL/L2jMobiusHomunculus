package org.l2jmobius.gameserver.model.actor.stat;

import org.l2jmobius.gameserver.model.actor.instance.StaticObjectInstance;

public class StaticObjectStat extends CreatureStat
{
	public StaticObjectStat(StaticObjectInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public StaticObjectInstance getActiveChar()
	{
		return (StaticObjectInstance) super.getActiveChar();
	}
	
	@Override
	public int getLevel()
	{
		return (byte) getActiveChar().getLevel();
	}
}
