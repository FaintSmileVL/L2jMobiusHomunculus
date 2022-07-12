package org.l2jmobius.gameserver.model.instancezone.conditions;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Distance instance condition
 * @author malyelfik
 */
public class ConditionDistance extends Condition
{
	public ConditionDistance(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, onlyLeader, showMessageAndHtml);
		setSystemMessage(SystemMessageId.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED, (message, player) -> message.addString(player.getName()));
	}
	
	@Override
	public boolean test(PlayerInstance player, Npc npc)
	{
		final int distance = getParameters().getInt("distance", 1000);
		return player.isInsideRadius3D(npc, distance);
	}
}
