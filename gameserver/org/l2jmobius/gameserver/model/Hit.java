package org.l2jmobius.gameserver.model;

import java.lang.ref.WeakReference;

import org.l2jmobius.gameserver.enums.AttackType;
import org.l2jmobius.gameserver.model.actor.Creature;

/**
 * @author UnAfraid
 */
public class Hit
{
	private final WeakReference<WorldObject> _target;
	private final int _targetId;
	private final int _damage;
	private final int _ssGrade;
	private int _flags = 0;
	
	public Hit(WorldObject target, int damage, boolean miss, boolean crit, byte shld, boolean soulshot, int ssGrade)
	{
		_target = new WeakReference<>(target);
		_targetId = target.getObjectId();
		_damage = damage;
		_ssGrade = ssGrade;
		
		if (miss)
		{
			addMask(AttackType.MISSED);
			return;
		}
		
		if (crit)
		{
			addMask(AttackType.CRITICAL);
		}
		
		if (soulshot)
		{
			addMask(AttackType.SHOT_USED);
		}
		
		if ((target.isCreature() && ((Creature) target).isHpBlocked()) || (shld > 0))
		{
			addMask(AttackType.BLOCKED);
		}
	}
	
	private void addMask(AttackType type)
	{
		_flags |= type.getMask();
	}
	
	public WorldObject getTarget()
	{
		return _target.get();
	}
	
	public int getTargetId()
	{
		return _targetId;
	}
	
	public int getDamage()
	{
		return _damage;
	}
	
	public int getFlags()
	{
		return _flags;
	}
	
	public int getGrade()
	{
		return _ssGrade;
	}
	
	public boolean isMiss()
	{
		return (AttackType.MISSED.getMask() & _flags) != 0;
	}
	
	public boolean isCritical()
	{
		return (AttackType.CRITICAL.getMask() & _flags) != 0;
	}
	
	public boolean isShotUsed()
	{
		return (AttackType.SHOT_USED.getMask() & _flags) != 0;
	}
	
	public boolean isBlocked()
	{
		return (AttackType.BLOCKED.getMask() & _flags) != 0;
	}
}
