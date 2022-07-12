package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * This class represents Friendly Mobs lying over the world.<br>
 * These friendly mobs should only attack players with karma > 0 and it is always aggro, since it just attacks players with karma.
 */
public class FriendlyMobInstance extends Attackable
{
	public FriendlyMobInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.FriendlyMobInstance);
	}
	
	@Override
	public boolean isAutoAttackable(Creature attacker)
	{
		if (attacker.isPlayer())
		{
			return ((PlayerInstance) attacker).getReputation() < 0;
		}
		return super.isAutoAttackable(attacker);
	}
	
	@Override
	public boolean isAggressive()
	{
		return true;
	}
}
