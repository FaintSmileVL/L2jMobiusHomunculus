package org.l2jmobius.gameserver.model.actor.instance;

import java.util.List;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.instancemanager.ZoneManager;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Tower;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * Class for Flame Control Tower instance.
 * @author JIV
 */
public class FlameTowerInstance extends Tower
{
	private int _upgradeLevel = 0;
	private List<Integer> _zoneList;
	
	public FlameTowerInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.FlameTowerInstance);
	}
	
	@Override
	public boolean doDie(Creature killer)
	{
		enableZones(false);
		return super.doDie(killer);
	}
	
	@Override
	public boolean deleteMe()
	{
		enableZones(false);
		return super.deleteMe();
	}
	
	public void enableZones(boolean value)
	{
		if ((_zoneList != null) && (_upgradeLevel != 0))
		{
			final int maxIndex = _upgradeLevel * 2;
			for (int i = 0; i < maxIndex; i++)
			{
				final ZoneType zone = ZoneManager.getInstance().getZoneById(_zoneList.get(i));
				if (zone != null)
				{
					zone.setEnabled(value);
				}
			}
		}
	}
	
	public void setUpgradeLevel(int level)
	{
		_upgradeLevel = level;
	}
	
	public void setZoneList(List<Integer> list)
	{
		_zoneList = list;
		enableZones(true);
	}
}