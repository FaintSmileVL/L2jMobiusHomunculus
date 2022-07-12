package org.l2jmobius.gameserver.model.conditions;

import java.util.List;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerHasPet.
 */
public class ConditionPlayerHasPet extends Condition
{
	private final List<Integer> _controlItemIds;
	
	/**
	 * Instantiates a new condition player has pet.
	 * @param itemIds the item ids
	 */
	public ConditionPlayerHasPet(List<Integer> itemIds)
	{
		if ((itemIds.size() == 1) && (itemIds.get(0) == 0))
		{
			_controlItemIds = null;
		}
		else
		{
			_controlItemIds = itemIds;
		}
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final Summon pet = effector.getActingPlayer().getPet();
		if ((effector.getActingPlayer() == null) || (pet == null))
		{
			return false;
		}
		
		if (_controlItemIds == null)
		{
			return true;
		}
		
		final ItemInstance controlItem = ((PetInstance) pet).getControlItem();
		return (controlItem != null) && _controlItemIds.contains(controlItem.getId());
	}
}
