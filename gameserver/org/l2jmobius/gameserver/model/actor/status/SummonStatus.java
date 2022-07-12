package org.l2jmobius.gameserver.model.actor.status;

import org.l2jmobius.gameserver.model.Duel;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.stats.Stat;
import org.l2jmobius.gameserver.util.Util;

public class SummonStatus extends PlayableStatus
{
	public SummonStatus(Summon activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public void reduceHp(double value, Creature attacker)
	{
		reduceHp(value, attacker, true, false, false);
	}
	
	@Override
	public void reduceHp(double amount, Creature attacker, boolean awake, boolean isDOT, boolean isHPConsumption)
	{
		if ((attacker == null) || getActiveChar().isDead())
		{
			return;
		}
		
		final PlayerInstance attackerPlayer = attacker.getActingPlayer();
		if ((attackerPlayer != null) && ((getActiveChar().getOwner() == null) || (getActiveChar().getOwner().getDuelId() != attackerPlayer.getDuelId())))
		{
			attackerPlayer.setDuelState(Duel.DUELSTATE_INTERRUPTED);
		}
		
		double value = amount;
		final PlayerInstance caster = getActiveChar().getTransferingDamageTo();
		if (getActiveChar().getOwner().getParty() != null)
		{
			if ((caster != null) && Util.checkIfInRange(1000, getActiveChar(), caster, true) && !caster.isDead() && getActiveChar().getParty().getMembers().contains(caster))
			{
				int transferDmg = ((int) value * (int) getActiveChar().getStat().getValue(Stat.TRANSFER_DAMAGE_TO_PLAYER, 0)) / 100;
				transferDmg = Math.min((int) caster.getCurrentHp() - 1, transferDmg);
				if (transferDmg > 0)
				{
					int membersInRange = 0;
					for (PlayerInstance member : caster.getParty().getMembers())
					{
						if (Util.checkIfInRange(1000, member, caster, false) && (member != caster))
						{
							membersInRange++;
						}
					}
					if (attacker.isPlayable() && (caster.getCurrentCp() > 0))
					{
						if (caster.getCurrentCp() > transferDmg)
						{
							caster.getStatus().reduceCp(transferDmg);
						}
						else
						{
							transferDmg = (int) (transferDmg - caster.getCurrentCp());
							caster.getStatus().reduceCp((int) caster.getCurrentCp());
						}
					}
					if (membersInRange > 0)
					{
						caster.reduceCurrentHp(transferDmg / membersInRange, attacker, null);
						value -= transferDmg;
					}
				}
			}
		}
		else if ((caster != null) && (caster == getActiveChar().getOwner()) && Util.checkIfInRange(1000, getActiveChar(), caster, true) && !caster.isDead()) // when no party, transfer only to owner (caster)
		{
			int transferDmg = ((int) value * (int) getActiveChar().getStat().getValue(Stat.TRANSFER_DAMAGE_TO_PLAYER, 0)) / 100;
			transferDmg = Math.min((int) caster.getCurrentHp() - 1, transferDmg);
			if (transferDmg > 0)
			{
				if (attacker.isPlayable() && (caster.getCurrentCp() > 0))
				{
					if (caster.getCurrentCp() > transferDmg)
					{
						caster.getStatus().reduceCp(transferDmg);
					}
					else
					{
						transferDmg = (int) (transferDmg - caster.getCurrentCp());
						caster.getStatus().reduceCp((int) caster.getCurrentCp());
					}
				}
				
				caster.reduceCurrentHp(transferDmg, attacker, null);
				value -= transferDmg;
			}
		}
		
		super.reduceHp(value, attacker, awake, isDOT, isHPConsumption);
	}
	
	@Override
	public Summon getActiveChar()
	{
		return (Summon) super.getActiveChar();
	}
}
