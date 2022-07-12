package org.l2jmobius.gameserver.model.actor;

import org.l2jmobius.gameserver.ai.CtrlIntention;
import org.l2jmobius.gameserver.geoengine.GeoEngine;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

/**
 * This class is a super-class for ControlTowerInstance and FlameTowerInstance.
 * @author Zoey76
 */
public abstract class Tower extends Npc
{
	public Tower(NpcTemplate template)
	{
		super(template);
		setInvul(false);
	}
	
	@Override
	public boolean canBeAttacked()
	{
		// Attackable during siege by attacker only
		return (getCastle() != null) && (getCastle().getResidenceId() > 0) && getCastle().getSiege().isInProgress();
	}
	
	@Override
	public boolean isAutoAttackable(Creature attacker)
	{
		// Attackable during siege by attacker only
		return (attacker != null) && attacker.isPlayer() && (getCastle() != null) && (getCastle().getResidenceId() > 0) && getCastle().getSiege().isInProgress() && getCastle().getSiege().checkIsAttacker(((PlayerInstance) attacker).getClan());
	}
	
	@Override
	public void onAction(PlayerInstance player, boolean interact)
	{
		if (!canTarget(player))
		{
			return;
		}
		
		if (this != player.getTarget())
		{
			// Set the target of the PlayerInstance player
			player.setTarget(this);
		}
		else if (interact && isAutoAttackable(player) && (Math.abs(player.getZ() - getZ()) < 100) && GeoEngine.getInstance().canSeeTarget(player, this))
		{
			// Notify the PlayerInstance AI with AI_INTENTION_INTERACT
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, this);
		}
		// Send a Server->Client ActionFailed to the PlayerInstance in order to avoid that the client wait another packet
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	@Override
	public void onForcedAttack(PlayerInstance player)
	{
		onAction(player);
	}
}
