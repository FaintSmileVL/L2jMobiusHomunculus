package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.ai.CreatureAI;
import org.l2jmobius.gameserver.ai.CtrlIntention;
import org.l2jmobius.gameserver.ai.FriendlyNpcAI;
import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.creature.npc.OnAttackableAttack;
import org.l2jmobius.gameserver.model.events.impl.creature.npc.OnAttackableKill;
import org.l2jmobius.gameserver.model.events.impl.creature.npc.OnNpcFirstTalk;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

/**
 * @author GKR, Sdw
 */
public class FriendlyNpcInstance extends Attackable
{
	private boolean _isAutoAttackable = true;
	
	public FriendlyNpcInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.FriendlyNpcInstance);
	}
	
	@Override
	public boolean isAttackable()
	{
		return false;
	}
	
	@Override
	public boolean isAutoAttackable(Creature attacker)
	{
		return _isAutoAttackable && !attacker.isPlayable() && !(attacker instanceof FriendlyNpcInstance);
	}
	
	@Override
	public void setAutoAttackable(boolean value)
	{
		_isAutoAttackable = value;
	}
	
	@Override
	public void addDamage(Creature attacker, int damage, Skill skill)
	{
		if (!attacker.isPlayable() && !(attacker instanceof FriendlyNpcInstance))
		{
			super.addDamage(attacker, damage, skill);
		}
		
		if (attacker.isAttackable())
		{
			EventDispatcher.getInstance().notifyEventAsync(new OnAttackableAttack(null, this, damage, skill, false), this);
		}
	}
	
	@Override
	public void addDamageHate(Creature attacker, int damage, int aggro)
	{
		if (!attacker.isPlayable() && !(attacker instanceof FriendlyNpcInstance))
		{
			super.addDamageHate(attacker, damage, aggro);
		}
	}
	
	@Override
	public boolean doDie(Creature killer)
	{
		// Kill the NpcInstance (the corpse disappeared after 7 seconds)
		if (!super.doDie(killer))
		{
			return false;
		}
		
		if ((killer != null) && killer.isAttackable())
		{
			// Delayed notification
			EventDispatcher.getInstance().notifyEventAsync(new OnAttackableKill(null, this, false), this);
		}
		return true;
	}
	
	@Override
	public void onAction(PlayerInstance player, boolean interact)
	{
		if (!canTarget(player))
		{
			return;
		}
		
		// Check if the PlayerInstance already target the GuardInstance
		if (getObjectId() != player.getTargetId())
		{
			// Set the target of the PlayerInstance player
			player.setTarget(this);
		}
		else if (interact)
		{
			// Calculate the distance between the PlayerInstance and the NpcInstance
			if (!canInteract(player))
			{
				// Set the PlayerInstance Intention to AI_INTENTION_INTERACT
				player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
			}
			else
			{
				player.setLastFolkNPC(this);
				
				// Open a chat window on client with the text of the GuardInstance
				if (hasListener(EventType.ON_NPC_QUEST_START))
				{
					player.setLastQuestNpcObject(getObjectId());
				}
				
				if (hasListener(EventType.ON_NPC_FIRST_TALK))
				{
					EventDispatcher.getInstance().notifyEventAsync(new OnNpcFirstTalk(this, player), this);
				}
				else
				{
					showChatWindow(player, 0);
				}
			}
		}
		// Send a Server->Client ActionFailed to the PlayerInstance in order to avoid that the client wait another packet
		player.sendPacket(ActionFailed.STATIC_PACKET);
	}
	
	@Override
	public String getHtmlPath(int npcId, int value, PlayerInstance player)
	{
		String pom = "";
		if (value == 0)
		{
			pom = Integer.toString(npcId);
		}
		else
		{
			pom = npcId + "-" + value;
		}
		return "data/html/default/" + pom + ".htm";
	}
	
	@Override
	protected CreatureAI initAI()
	{
		return new FriendlyNpcAI(this);
	}
}
