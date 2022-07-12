package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author UnAfraid
 */
public class OnNpcSkillSee implements IBaseEvent
{
	private final Npc _npc;
	private final PlayerInstance _caster;
	private final Skill _skill;
	private final WorldObject[] _targets;
	private final boolean _isSummon;
	
	public OnNpcSkillSee(Npc npc, PlayerInstance caster, Skill skill, boolean isSummon, WorldObject... targets)
	{
		_npc = npc;
		_caster = caster;
		_skill = skill;
		_isSummon = isSummon;
		_targets = targets;
	}
	
	public Npc getTarget()
	{
		return _npc;
	}
	
	public PlayerInstance getCaster()
	{
		return _caster;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
	
	public WorldObject[] getTargets()
	{
		return _targets;
	}
	
	public boolean isSummon()
	{
		return _isSummon;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_SKILL_SEE;
	}
}
