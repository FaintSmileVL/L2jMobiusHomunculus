package org.l2jmobius.gameserver.model.conditions;

import java.util.Set;

import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * Condition Category Type implementation.
 * @author Adry_85
 */
public class ConditionCategoryType extends Condition
{
	private final Set<CategoryType> _categoryTypes;
	
	public ConditionCategoryType(Set<CategoryType> categoryTypes)
	{
		_categoryTypes = categoryTypes;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		for (CategoryType type : _categoryTypes)
		{
			if (effector.isInCategory(type))
			{
				return true;
			}
		}
		return false;
	}
}
