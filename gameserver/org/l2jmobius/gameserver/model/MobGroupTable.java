package org.l2jmobius.gameserver.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.instance.ControllableMobInstance;

/**
 * @author littlecrow
 */
public class MobGroupTable
{
	private final Map<Integer, MobGroup> _groupMap = new ConcurrentHashMap<>();
	
	public static final int FOLLOW_RANGE = 300;
	public static final int RANDOM_RANGE = 300;
	
	protected MobGroupTable()
	{
	}
	
	public static MobGroupTable getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	public void addGroup(int groupKey, MobGroup group)
	{
		_groupMap.put(groupKey, group);
	}
	
	public MobGroup getGroup(int groupKey)
	{
		return _groupMap.get(groupKey);
	}
	
	public int getGroupCount()
	{
		return _groupMap.size();
	}
	
	public MobGroup getGroupForMob(ControllableMobInstance mobInst)
	{
		for (MobGroup mobGroup : _groupMap.values())
		{
			if (mobGroup.isGroupMember(mobInst))
			{
				return mobGroup;
			}
		}
		return null;
	}
	
	public MobGroup[] getGroups()
	{
		return _groupMap.values().toArray(new MobGroup[_groupMap.size()]);
	}
	
	public boolean removeGroup(int groupKey)
	{
		return _groupMap.remove(groupKey) != null;
	}
	
	private static class SingletonHolder
	{
		protected static final MobGroupTable INSTANCE = new MobGroupTable();
	}
}