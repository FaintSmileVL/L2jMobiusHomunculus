package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerPledgeClass.
 * @author MrPoke
 */
public class ConditionPlayerPledgeClass extends Condition
{
	private final int _pledgeClass;
	
	/**
	 * Instantiates a new condition player pledge class.
	 * @param pledgeClass the pledge class
	 */
	public ConditionPlayerPledgeClass(int pledgeClass)
	{
		_pledgeClass = pledgeClass;
	}
	
	/**
	 * Test impl.
	 * @return true, if successful
	 */
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final PlayerInstance player = effector.getActingPlayer();
		if ((player == null) || (player.getClan() == null))
		{
			return false;
		}
		
		final boolean isClanLeader = player.isClanLeader();
		if ((_pledgeClass == -1) && !isClanLeader)
		{
			return false;
		}
		
		return isClanLeader || (player.getPledgeClass() >= _pledgeClass);
	}
}
