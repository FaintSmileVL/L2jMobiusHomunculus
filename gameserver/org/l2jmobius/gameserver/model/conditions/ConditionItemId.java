package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionItemId.
 * @author mkizub
 */
public class ConditionItemId extends Condition
{
	private final int _itemId;
	
	/**
	 * Instantiates a new condition item id.
	 * @param itemId the item id
	 */
	public ConditionItemId(int itemId)
	{
		_itemId = itemId;
	}
	
	/**
	 * Test impl.
	 * @return true, if successful
	 */
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		return (item != null) && (item.getId() == _itemId);
	}
}
