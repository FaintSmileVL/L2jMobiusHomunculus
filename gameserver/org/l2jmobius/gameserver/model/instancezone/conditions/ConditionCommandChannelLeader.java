package org.l2jmobius.gameserver.model.instancezone.conditions;

import org.l2jmobius.gameserver.model.AbstractPlayerGroup;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Command channel leader condition
 * @author malyelfik
 */
public class ConditionCommandChannelLeader extends Condition
{
	public ConditionCommandChannelLeader(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, true, showMessageAndHtml);
		setSystemMessage(SystemMessageId.ONLY_A_PARTY_LEADER_CAN_MAKE_THE_REQUEST_TO_ENTER);
	}
	
	@Override
	public boolean test(PlayerInstance player, Npc npc)
	{
		final AbstractPlayerGroup group = player.getCommandChannel();
		return (group != null) && group.isLeader(player);
	}
}