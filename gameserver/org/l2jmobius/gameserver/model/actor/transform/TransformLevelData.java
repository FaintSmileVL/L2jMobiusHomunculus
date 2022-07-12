package org.l2jmobius.gameserver.model.actor.transform;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.stats.Stat;

/**
 * @author UnAfraid
 */
public class TransformLevelData
{
	private final int _level;
	private final double _levelMod;
	private Map<Integer, Double> _stats;
	
	public TransformLevelData(StatSet set)
	{
		_level = set.getInt("val");
		_levelMod = set.getDouble("levelMod");
		addStats(Stat.MAX_HP, set.getDouble("hp"));
		addStats(Stat.MAX_MP, set.getDouble("mp"));
		addStats(Stat.MAX_CP, set.getDouble("cp"));
		addStats(Stat.REGENERATE_HP_RATE, set.getDouble("hpRegen"));
		addStats(Stat.REGENERATE_MP_RATE, set.getDouble("mpRegen"));
		addStats(Stat.REGENERATE_CP_RATE, set.getDouble("cpRegen"));
	}
	
	private void addStats(Stat stat, double value)
	{
		if (_stats == null)
		{
			_stats = new HashMap<>();
		}
		_stats.put(stat.ordinal(), value);
	}
	
	public double getStats(Stat stat, double defaultValue)
	{
		return _stats == null ? defaultValue : _stats.getOrDefault(stat.ordinal(), defaultValue);
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public double getLevelMod()
	{
		return _levelMod;
	}
}
