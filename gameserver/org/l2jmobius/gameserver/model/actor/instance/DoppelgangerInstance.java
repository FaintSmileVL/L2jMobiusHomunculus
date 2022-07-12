package org.l2jmobius.gameserver.model.actor.instance;

import java.util.logging.Logger;

import util.Rnd;
import org.l2jmobius.gameserver.ai.CreatureAI;
import org.l2jmobius.gameserver.ai.CtrlIntention;
import org.l2jmobius.gameserver.ai.DoppelgangerAI;
import org.l2jmobius.gameserver.enums.Team;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.model.effects.EffectFlag;
import org.l2jmobius.gameserver.model.olympiad.OlympiadGameManager;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Nik
 */
public class DoppelgangerInstance extends Npc
{
	protected static final Logger log = Logger.getLogger(DoppelgangerInstance.class.getName());
	
	private boolean _copySummonerEffects = true;
	
	public DoppelgangerInstance(NpcTemplate template, PlayerInstance owner)
	{
		super(template);
		
		setSummoner(owner);
		setCloneObjId(owner.getObjectId());
		setClanId(owner.getClanId());
		setInstance(owner.getInstanceWorld()); // set instance to same as owner
		setXYZInvisible(owner.getX() + Rnd.get(-100, 100), owner.getY() + Rnd.get(-100, 100), owner.getZ());
		((DoppelgangerAI) getAI()).setStartFollowController(true);
		followSummoner(true);
	}
	
	@Override
	protected CreatureAI initAI()
	{
		return new DoppelgangerAI(this);
	}
	
	@Override
	public void onSpawn()
	{
		super.onSpawn();
		
		if (_copySummonerEffects && (getSummoner() != null))
		{
			for (BuffInfo summonerInfo : getSummoner().getEffectList().getEffects())
			{
				if (summonerInfo.getAbnormalTime() > 0)
				{
					final BuffInfo info = new BuffInfo(getSummoner(), this, summonerInfo.getSkill(), false, null, null);
					info.setAbnormalTime(summonerInfo.getAbnormalTime());
					getEffectList().add(info);
				}
			}
		}
	}
	
	public void followSummoner(boolean followSummoner)
	{
		if (followSummoner)
		{
			if ((getAI().getIntention() == CtrlIntention.AI_INTENTION_IDLE) || (getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE))
			{
				setRunning();
				getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, getSummoner());
			}
		}
		else if (getAI().getIntention() == CtrlIntention.AI_INTENTION_FOLLOW)
		{
			getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
	}
	
	public void setCopySummonerEffects(boolean copySummonerEffects)
	{
		_copySummonerEffects = copySummonerEffects;
	}
	
	@Override
	public byte getPvpFlag()
	{
		return getSummoner() != null ? getSummoner().getPvpFlag() : 0;
	}
	
	@Override
	public Team getTeam()
	{
		return getSummoner() != null ? getSummoner().getTeam() : Team.NONE;
	}
	
	@Override
	public boolean isAutoAttackable(Creature attacker)
	{
		return (getSummoner() != null) ? getSummoner().isAutoAttackable(attacker) : super.isAutoAttackable(attacker);
	}
	
	@Override
	public void doAttack(double damage, Creature target, Skill skill, boolean isDOT, boolean directlyToHp, boolean critical, boolean reflect)
	{
		super.doAttack(damage, target, skill, isDOT, directlyToHp, critical, reflect);
		sendDamageMessage(target, skill, (int) damage, critical, false);
	}
	
	@Override
	public void sendDamageMessage(Creature target, Skill skill, int damage, boolean crit, boolean miss)
	{
		if (miss || (getSummoner() == null) || !getSummoner().isPlayer())
		{
			return;
		}
		
		// Prevents the double spam of system messages, if the target is the owning player.
		if (target.getObjectId() != getSummoner().getObjectId())
		{
			if (getActingPlayer().isInOlympiadMode() && (target.isPlayer()) && ((PlayerInstance) target).isInOlympiadMode() && (((PlayerInstance) target).getOlympiadGameId() == getActingPlayer().getOlympiadGameId()))
			{
				OlympiadGameManager.getInstance().notifyCompetitorDamage(getSummoner().getActingPlayer(), damage);
			}
			
			final SystemMessage sm;
			if ((target.isHpBlocked() && !target.isNpc()) || (target.isPlayer() && target.isAffected(EffectFlag.DUELIST_FURY) && !getActingPlayer().isAffected(EffectFlag.FACEOFF)))
			{
				sm = new SystemMessage(SystemMessageId.THE_ATTACK_HAS_BEEN_BLOCKED);
			}
			else
			{
				sm = new SystemMessage(SystemMessageId.C1_HAS_INFLICTED_S3_DAMAGE_ON_C2);
				sm.addNpcName(this);
				sm.addString(target.getName());
				sm.addInt(damage);
				sm.addPopup(target.getObjectId(), getObjectId(), (damage * -1));
			}
			
			sendPacket(sm);
		}
	}
	
	@Override
	public void reduceCurrentHp(double damage, Creature attacker, Skill skill)
	{
		super.reduceCurrentHp(damage, attacker, skill);
		
		if ((getSummoner() != null) && getSummoner().isPlayer() && (attacker != null) && !isDead() && !isHpBlocked())
		{
			final SystemMessage sm = new SystemMessage(SystemMessageId.C1_HAS_RECEIVED_S3_DAMAGE_FROM_C2);
			sm.addNpcName(this);
			sm.addString(attacker.getName());
			sm.addInt((int) damage);
			sm.addPopup(getObjectId(), attacker.getObjectId(), (int) -damage);
			sendPacket(sm);
		}
	}
	
	@Override
	public PlayerInstance getActingPlayer()
	{
		return getSummoner() != null ? getSummoner().getActingPlayer() : super.getActingPlayer();
	}
	
	@Override
	public void onTeleported()
	{
		deleteMe(); // In retail, doppelgangers disappear when summoner teleports.
	}
	
	@Override
	public void sendPacket(IClientOutgoingPacket... packets)
	{
		if (getSummoner() != null)
		{
			getSummoner().sendPacket(packets);
		}
	}
	
	@Override
	public void sendPacket(SystemMessageId id)
	{
		if (getSummoner() != null)
		{
			getSummoner().sendPacket(id);
		}
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "(" + getId() + ") Summoner: " + getSummoner();
	}
}
