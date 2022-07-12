package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author UnAfraid
 */
public class OnNpcSkillFinished implements IBaseEvent
{
	private final Npc _caster;
	private final PlayerInstance _target;
	private final Skill _skill;
	
	public OnNpcSkillFinished(Npc caster, PlayerInstance target, Skill skill)
	{
		_caster = caster;
		_target = target;
		_skill = skill;
	}
	
	public PlayerInstance getTarget()
	{
		return _target;
	}
	
	public Npc getCaster()
	{
		return _caster;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_SKILL_FINISHED;
	}
}
