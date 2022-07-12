package org.l2jmobius.gameserver.model.holders;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.enums.Faction;

/**
 * @author Mobius
 */
public class MonsterBookCardHolder
{
	private final int _id;
	private final int _monsterId;
	private final Faction _faction;
	private final List<MonsterBookRewardHolder> _rewards = new ArrayList<>(4);
	
	public MonsterBookCardHolder(int id, int monsterId, Faction faction)
	{
		_id = id;
		_monsterId = monsterId;
		_faction = faction;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getMonsterId()
	{
		return _monsterId;
	}
	
	public Faction getFaction()
	{
		return _faction;
	}
	
	public MonsterBookRewardHolder getReward(int level)
	{
		return _rewards.get(level);
	}
	
	public void addReward(MonsterBookRewardHolder reward)
	{
		_rewards.add(reward);
	}
}
