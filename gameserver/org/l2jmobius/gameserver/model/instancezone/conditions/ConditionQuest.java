package org.l2jmobius.gameserver.model.instancezone.conditions;

import org.l2jmobius.gameserver.instancemanager.QuestManager;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.model.quest.Quest;
import org.l2jmobius.gameserver.model.quest.QuestState;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Instance quest condition
 * @author malyelfik
 */
public class ConditionQuest extends Condition
{
	public ConditionQuest(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, onlyLeader, showMessageAndHtml);
		// Set message
		setSystemMessage(SystemMessageId.C1_S_QUEST_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED, (message, player) -> message.addString(player.getName()));
	}
	
	@Override
	protected boolean test(PlayerInstance player, Npc npc)
	{
		final int id = getParameters().getInt("id");
		final Quest q = QuestManager.getInstance().getQuest(id);
		if (q == null)
		{
			return false;
		}
		
		final QuestState qs = player.getQuestState(q.getName());
		if (qs == null)
		{
			return false;
		}
		
		final int cond = getParameters().getInt("cond", -1);
		return (cond == -1) || qs.isCond(cond);
	}
}
