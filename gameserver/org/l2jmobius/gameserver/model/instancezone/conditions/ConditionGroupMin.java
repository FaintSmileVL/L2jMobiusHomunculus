package org.l2jmobius.gameserver.model.instancezone.conditions;

import java.util.List;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Instance enter group min size
 * @author malyelfik
 */
public class ConditionGroupMin extends Condition
{
	public ConditionGroupMin(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, true, showMessageAndHtml);
		setSystemMessage(SystemMessageId.YOU_MUST_HAVE_A_MINIMUM_OF_S1_PEOPLE_TO_ENTER_THIS_INSTANCE_ZONE, (msg, player) -> msg.addInt(getLimit()));
	}
	
	@Override
	protected boolean test(PlayerInstance player, Npc npc, List<PlayerInstance> group)
	{
		return group.size() >= getLimit();
	}
	
	public int getLimit()
	{
		return getParameters().getInt("limit");
	}
}
