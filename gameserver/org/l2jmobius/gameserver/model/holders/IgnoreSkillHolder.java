package org.l2jmobius.gameserver.model.holders;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author UnAfraid
 */
public class IgnoreSkillHolder extends SkillHolder
{
	private final AtomicInteger _instances = new AtomicInteger(1);
	
	public IgnoreSkillHolder(int skillId, int skillLevel)
	{
		super(skillId, skillLevel);
	}
	
	public IgnoreSkillHolder(SkillHolder holder)
	{
		super(holder.getSkill());
	}
	
	public int getInstances()
	{
		return _instances.get();
	}
	
	public int increaseInstances()
	{
		return _instances.incrementAndGet();
	}
	
	public int decreaseInstances()
	{
		return _instances.decrementAndGet();
	}
}
