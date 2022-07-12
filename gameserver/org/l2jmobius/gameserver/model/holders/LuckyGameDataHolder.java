package org.l2jmobius.gameserver.model.holders;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.model.StatSet;

/**
 * @author Sdw
 */
public class LuckyGameDataHolder
{
	private final int _index;
	private final int _turningPoints;
	private final List<ItemChanceHolder> _commonRewards = new ArrayList<>();
	private final List<ItemPointHolder> _uniqueRewards = new ArrayList<>();
	private final List<ItemChanceHolder> _modifyRewards = new ArrayList<>();
	private int _minModifyRewardGame;
	private int _maxModifyRewardGame;
	
	public LuckyGameDataHolder(StatSet params)
	{
		_index = params.getInt("index");
		_turningPoints = params.getInt("turning_point");
	}
	
	public void addCommonReward(ItemChanceHolder item)
	{
		_commonRewards.add(item);
	}
	
	public void addUniqueReward(ItemPointHolder item)
	{
		_uniqueRewards.add(item);
	}
	
	public void addModifyReward(ItemChanceHolder item)
	{
		_modifyRewards.add(item);
	}
	
	public List<ItemChanceHolder> getCommonReward()
	{
		return _commonRewards;
	}
	
	public List<ItemPointHolder> getUniqueReward()
	{
		return _uniqueRewards;
	}
	
	public List<ItemChanceHolder> getModifyReward()
	{
		return _modifyRewards;
	}
	
	public void setMinModifyRewardGame(int minModifyRewardGame)
	{
		_minModifyRewardGame = minModifyRewardGame;
	}
	
	public void setMaxModifyRewardGame(int maxModifyRewardGame)
	{
		_maxModifyRewardGame = maxModifyRewardGame;
	}
	
	public int getMinModifyRewardGame()
	{
		return _minModifyRewardGame;
	}
	
	public int getMaxModifyRewardGame()
	{
		return _maxModifyRewardGame;
	}
	
	public int getIndex()
	{
		return _index;
	}
	
	public int getTurningPoints()
	{
		return _turningPoints;
	}
}
