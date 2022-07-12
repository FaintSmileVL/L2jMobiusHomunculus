package org.l2jmobius.gameserver.model;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author xban1x
 */
public class DamageDoneInfo
{
	private final PlayerInstance _attacker;
	private long _damage = 0;
	
	public DamageDoneInfo(PlayerInstance attacker)
	{
		_attacker = attacker;
	}
	
	public PlayerInstance getAttacker()
	{
		return _attacker;
	}
	
	public void addDamage(long damage)
	{
		_damage += damage;
	}
	
	public long getDamage()
	{
		return _damage;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (this == obj) || ((obj instanceof DamageDoneInfo) && (((DamageDoneInfo) obj).getAttacker() == _attacker));
	}
	
	@Override
	public int hashCode()
	{
		return _attacker.getObjectId();
	}
}
