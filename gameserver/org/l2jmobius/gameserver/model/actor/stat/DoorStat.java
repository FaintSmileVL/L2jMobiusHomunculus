package org.l2jmobius.gameserver.model.actor.stat;

import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;

/**
 * @author malyelfik
 */
public class DoorStat extends CreatureStat
{
	private int _upgradeHpRatio = 1;
	
	public DoorStat(DoorInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public DoorInstance getActiveChar()
	{
		return (DoorInstance) super.getActiveChar();
	}
	
	@Override
	public int getMaxHp()
	{
		return super.getMaxHp() * _upgradeHpRatio;
	}
	
	public void setUpgradeHpRatio(int ratio)
	{
		_upgradeHpRatio = ratio;
	}
	
	public int getUpgradeHpRatio()
	{
		return _upgradeHpRatio;
	}
}
