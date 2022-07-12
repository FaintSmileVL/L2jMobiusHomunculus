package org.l2jmobius.gameserver.model.instancezone.conditions;

import org.l2jmobius.gameserver.enums.ResidenceType;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;

/**
 * Instance residence condition
 * @author malyelfik
 */
public class ConditionHasResidence extends Condition
{
	public ConditionHasResidence(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, onlyLeader, showMessageAndHtml);
	}
	
	@Override
	protected boolean test(PlayerInstance player, Npc npc)
	{
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return false;
		}
		
		final StatSet params = getParameters();
		final int id = params.getInt("id");
		boolean test = false;
		switch (params.getEnum("type", ResidenceType.class))
		{
			case CASTLE:
			{
				test = clan.getCastleId() == id;
				break;
			}
			case FORTRESS:
			{
				test = clan.getFortId() == id;
				break;
			}
			case CLANHALL:
			{
				test = clan.getHideoutId() == id;
				break;
			}
		}
		return test;
	}
}