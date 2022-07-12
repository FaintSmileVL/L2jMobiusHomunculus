package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionTargetInvSize.
 * @author Zoey76
 */
public class ConditionTargetInvSize extends Condition
{
	private final int _size;
	
	/**
	 * Instantiates a new condition player inv size.
	 * @param size the size
	 */
	public ConditionTargetInvSize(int size)
	{
		_size = size;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if ((effected != null) && effected.isPlayer())
		{
			final PlayerInstance target = effected.getActingPlayer();
			return target.getInventory().getSize(i -> !i.isQuestItem()) <= (target.getInventoryLimit() - _size);
		}
		return false;
	}
}
