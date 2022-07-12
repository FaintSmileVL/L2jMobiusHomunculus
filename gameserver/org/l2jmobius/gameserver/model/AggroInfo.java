package org.l2jmobius.gameserver.model;

import org.l2jmobius.gameserver.model.actor.Creature;

/**
 * @author xban1x
 */
public class AggroInfo
{
	private final Creature _attacker;
	private int _hate = 0;
	private int _damage = 0;
	
	public AggroInfo(Creature pAttacker)
	{
		_attacker = pAttacker;
	}
	
	public Creature getAttacker()
	{
		return _attacker;
	}
	
	public int getHate()
	{
		return _hate;
	}
	
	public int checkHate(Creature owner)
	{
		if (_attacker.isAlikeDead() || !_attacker.isSpawned() || !owner.isInSurroundingRegion(_attacker))
		{
			_hate = 0;
		}
		return _hate;
	}
	
	public void addHate(int value)
	{
		_hate = (int) Math.min(_hate + (long) value, 999999999);
	}
	
	public void stopHate()
	{
		_hate = 0;
	}
	
	public int getDamage()
	{
		return _damage;
	}
	
	public void addDamage(int value)
	{
		_damage = (int) Math.min(_damage + (long) value, 999999999);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (obj instanceof AggroInfo)
		{
			return (((AggroInfo) obj).getAttacker() == _attacker);
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return _attacker.getObjectId();
	}
	
	@Override
	public String toString()
	{
		return "AggroInfo [attacker=" + _attacker + ", hate=" + _hate + ", damage=" + _damage + "]";
	}
}
