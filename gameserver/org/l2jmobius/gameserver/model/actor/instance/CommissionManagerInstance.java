package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExShowCommission;

/**
 * @author NosBit
 */
public class CommissionManagerInstance extends Npc
{
	public CommissionManagerInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.CommissionManagerInstance);
	}
	
	@Override
	public boolean isAutoAttackable(Creature attacker)
	{
		if (attacker.isMonster())
		{
			return true;
		}
		return super.isAutoAttackable(attacker);
	}
	
	@Override
	public void onBypassFeedback(PlayerInstance player, String command)
	{
		if (command.equalsIgnoreCase("show_commission"))
		{
			player.sendPacket(ExShowCommission.STATIC_PACKET);
		}
		else
		{
			super.onBypassFeedback(player, command);
		}
	}
}
