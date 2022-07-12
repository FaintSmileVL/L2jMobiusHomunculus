package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.actor.Creature;

/**
 * @author Mobius
 */
public class DamageTakenHolder
{
	private final Creature _creature;
	private final int _skillId;
	private final double _damage;
	
	public DamageTakenHolder(Creature creature, int skillId, double damage)
	{
		_creature = creature;
		_skillId = skillId;
		_damage = damage;
	}
	
	public Creature getCreature()
	{
		return _creature;
	}
	
	public int getSkillId()
	{
		return _skillId;
	}
	
	public double getDamage()
	{
		return _damage;
	}
}
