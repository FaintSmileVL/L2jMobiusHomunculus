package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * This class manages all Grand Bosses.
 * @version $Revision: 1.0.0.0 $ $Date: 2006/06/16 $
 */
public class GrandBossInstance extends MonsterInstance
{
	private boolean _useRaidCurse = true;
	
	/**
	 * Constructor for GrandBossInstance. This represent all grandbosses.
	 * @param template NpcTemplate of the instance
	 */
	public GrandBossInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.GrandBossInstance);
		setIsRaid(true);
		setLethalable(false);
	}
	
	@Override
	public void onSpawn()
	{
		setRandomWalking(false);
		super.onSpawn();
	}
	
	@Override
	public int getVitalityPoints(int level, double exp, boolean isBoss)
	{
		return -super.getVitalityPoints(level, exp, isBoss);
	}
	
	@Override
	public boolean useVitalityRate()
	{
		return false;
	}
	
	public void setUseRaidCurse(boolean value)
	{
		_useRaidCurse = value;
	}
	
	@Override
	public boolean giveRaidCurse()
	{
		return _useRaidCurse;
	}
}
