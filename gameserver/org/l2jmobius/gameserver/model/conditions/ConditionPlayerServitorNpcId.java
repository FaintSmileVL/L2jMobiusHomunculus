package org.l2jmobius.gameserver.model.conditions;

import java.util.List;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerServitorNpcId.
 */
public class ConditionPlayerServitorNpcId extends Condition
{
	private final List<Integer> _npcIds;
	
	/**
	 * Instantiates a new condition player servitor npc id.
	 * @param npcIds the npc ids
	 */
	public ConditionPlayerServitorNpcId(List<Integer> npcIds)
	{
		if ((npcIds.size() == 1) && (npcIds.get(0) == 0))
		{
			_npcIds = null;
		}
		else
		{
			_npcIds = npcIds;
		}
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if ((effector.getActingPlayer() == null) || !effector.getActingPlayer().hasSummon())
		{
			return false;
		}
		if (_npcIds == null)
		{
			return true;
		}
		for (Summon summon : effector.getServitors().values())
		{
			if (_npcIds.contains(summon.getId()))
			{
				return true;
			}
		}
		return false;
	}
}
