package org.l2jmobius.gameserver.model.events.impl.creature;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * An instantly executed event when Caster has finished using a skill.
 * @author Nik
 */
public class OnCreatureSkillFinishCast implements IBaseEvent
{
	private final Creature _caster;
	private final Skill _skill;
	private final boolean _simultaneously;
	private final WorldObject _target;
	
	public OnCreatureSkillFinishCast(Creature caster, WorldObject target, Skill skill, boolean simultaneously)
	{
		_caster = caster;
		_skill = skill;
		_simultaneously = simultaneously;
		_target = target;
	}
	
	public Creature getCaster()
	{
		return _caster;
	}
	
	public WorldObject getTarget()
	{
		return _target;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
	
	public boolean isSimultaneously()
	{
		return _simultaneously;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CREATURE_SKILL_FINISH_CAST;
	}
}