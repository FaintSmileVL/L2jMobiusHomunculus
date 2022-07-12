package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerInvSize.
 * @author Kerberos
 */
public class ConditionPlayerInvSize extends Condition
{
	private final int _size;
	
	/**
	 * Instantiates a new condition player inv size.
	 * @param size the size
	 */
	public ConditionPlayerInvSize(int size)
	{
		_size = size;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effector.getActingPlayer() != null)
		{
			return effector.getActingPlayer().getInventory().getSize(i -> !i.isQuestItem()) <= (effector.getActingPlayer().getInventoryLimit() - _size);
		}
		return true;
	}
}
