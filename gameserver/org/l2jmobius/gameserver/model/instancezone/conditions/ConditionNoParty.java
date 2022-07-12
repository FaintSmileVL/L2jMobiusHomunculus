package org.l2jmobius.gameserver.model.instancezone.conditions;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;

/**
 * Instance no party condition
 * @author St3eT
 */
public class ConditionNoParty extends Condition
{
	public ConditionNoParty(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, true, showMessageAndHtml);
	}
	
	@Override
	public boolean test(PlayerInstance player, Npc npc)
	{
		return !player.isInParty();
	}
}
