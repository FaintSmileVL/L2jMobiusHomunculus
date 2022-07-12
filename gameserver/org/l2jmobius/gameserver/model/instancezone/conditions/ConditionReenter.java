package org.l2jmobius.gameserver.model.instancezone.conditions;

import util.Chronos;
import org.l2jmobius.gameserver.instancemanager.InstanceManager;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Instance reenter conditions
 * @author malyelfik
 */
public class ConditionReenter extends Condition
{
	public ConditionReenter(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, onlyLeader, showMessageAndHtml);
		setSystemMessage(SystemMessageId.C1_MAY_NOT_RE_ENTER_YET, (message, player) -> message.addString(player.getName()));
	}
	
	@Override
	protected boolean test(PlayerInstance player, Npc npc)
	{
		final int instanceId = getParameters().getInt("instanceId", getInstanceTemplate().getId());
		return Chronos.currentTimeMillis() > InstanceManager.getInstance().getInstanceTime(player, instanceId);
	}
}