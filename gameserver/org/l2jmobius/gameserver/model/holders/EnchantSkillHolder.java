package org.l2jmobius.gameserver.model.holders;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.l2jmobius.gameserver.enums.SkillEnchantType;
import org.l2jmobius.gameserver.model.StatSet;

/**
 * @author Sdw
 */
public class EnchantSkillHolder
{
	private final int _level;
	private final int _enchantFailLevel;
	private final Map<SkillEnchantType, Long> _sp = new EnumMap<>(SkillEnchantType.class);
	private final Map<SkillEnchantType, Integer> _chance = new EnumMap<>(SkillEnchantType.class);
	private final Map<SkillEnchantType, Set<ItemHolder>> _requiredItems = new EnumMap<>(SkillEnchantType.class);
	
	public EnchantSkillHolder(StatSet set)
	{
		_level = set.getInt("level");
		_enchantFailLevel = set.getInt("enchantFailLevel");
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public int getEnchantFailLevel()
	{
		return _enchantFailLevel;
	}
	
	public void addSp(SkillEnchantType type, long sp)
	{
		_sp.put(type, sp);
	}
	
	public long getSp(SkillEnchantType type)
	{
		final Long val = _sp.get(type);
		return val != null ? val.longValue() : 0;
	}
	
	public void addChance(SkillEnchantType type, int chance)
	{
		_chance.put(type, chance);
	}
	
	public int getChance(SkillEnchantType type)
	{
		final Integer val = _chance.get(type);
		return val != null ? val.intValue() : 100;
	}
	
	public void addRequiredItem(SkillEnchantType type, ItemHolder item)
	{
		_requiredItems.computeIfAbsent(type, k -> new HashSet<>()).add(item);
	}
	
	public Set<ItemHolder> getRequiredItems(SkillEnchantType type)
	{
		return _requiredItems.getOrDefault(type, Collections.emptySet());
	}
}
