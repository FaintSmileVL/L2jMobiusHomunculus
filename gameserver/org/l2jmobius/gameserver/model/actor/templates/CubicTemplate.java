package org.l2jmobius.gameserver.model.actor.templates;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.cubic.CubicInstance;
import org.l2jmobius.gameserver.model.cubic.CubicSkill;
import org.l2jmobius.gameserver.model.cubic.CubicTargetType;
import org.l2jmobius.gameserver.model.cubic.ICubicConditionHolder;
import org.l2jmobius.gameserver.model.cubic.conditions.ICubicCondition;

/**
 * @author UnAfraid
 */
public class CubicTemplate implements ICubicConditionHolder
{
	private final int _id;
	private final int _level;
	private final int _slot;
	private final int _duration;
	private final int _delay;
	private final int _maxCount;
	private final int _useUp;
	private final double _power;
	private final CubicTargetType _targetType;
	private final List<ICubicCondition> _conditions = new ArrayList<>();
	public List<CubicSkill> _skills = new ArrayList<>();
	
	public CubicTemplate(StatSet set)
	{
		_id = set.getInt("id");
		_level = set.getInt("level");
		_slot = set.getInt("slot");
		_duration = set.getInt("duration");
		_delay = set.getInt("delay");
		_maxCount = set.getInt("maxCount");
		_useUp = set.getInt("useUp");
		_power = set.getDouble("power");
		_targetType = set.getEnum("targetType", CubicTargetType.class, CubicTargetType.TARGET);
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public int getSlot()
	{
		return _slot;
	}
	
	public int getDuration()
	{
		return _duration;
	}
	
	public int getDelay()
	{
		return _delay;
	}
	
	public int getMaxCount()
	{
		return _maxCount;
	}
	
	public int getUseUp()
	{
		return _useUp;
	}
	
	public double getPower()
	{
		return _power;
	}
	
	public CubicTargetType getTargetType()
	{
		return _targetType;
	}
	
	public List<CubicSkill> getSkills()
	{
		return _skills;
	}
	
	@Override
	public boolean validateConditions(CubicInstance cubic, Creature owner, WorldObject target)
	{
		return _conditions.isEmpty() || _conditions.stream().allMatch(condition -> condition.test(cubic, owner, target));
	}
	
	@Override
	public void addCondition(ICubicCondition condition)
	{
		_conditions.add(condition);
	}
	
	@Override
	public String toString()
	{
		return "Cubic id: " + _id + " level: " + _level + " slot: " + _slot + " duration: " + _duration + " delay: " + _delay + " maxCount: " + _maxCount + " useUp: " + _useUp + " power: " + _power + Config.EOL + "skills: " + _skills + Config.EOL + "conditions:" + _conditions + Config.EOL;
	}
}
