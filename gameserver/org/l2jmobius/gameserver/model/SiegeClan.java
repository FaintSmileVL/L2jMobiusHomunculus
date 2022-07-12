package org.l2jmobius.gameserver.model;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.enums.SiegeClanType;
import org.l2jmobius.gameserver.model.actor.Npc;

public class SiegeClan
{
	private int _clanId = 0;
	private final Set<Npc> _flags = ConcurrentHashMap.newKeySet();
	private SiegeClanType _type;
	
	public SiegeClan(int clanId, SiegeClanType type)
	{
		_clanId = clanId;
		_type = type;
	}
	
	public int getNumFlags()
	{
		return _flags.size();
	}
	
	public void addFlag(Npc flag)
	{
		_flags.add(flag);
	}
	
	public boolean removeFlag(Npc flag)
	{
		if (flag == null)
		{
			return false;
		}
		
		flag.deleteMe();
		
		return _flags.remove(flag);
	}
	
	public void removeFlags()
	{
		for (Npc flag : _flags)
		{
			removeFlag(flag);
		}
	}
	
	public int getClanId()
	{
		return _clanId;
	}
	
	public Set<Npc> getFlag()
	{
		return _flags;
	}
	
	public SiegeClanType getType()
	{
		return _type;
	}
	
	public void setType(SiegeClanType setType)
	{
		_type = setType;
	}
}
