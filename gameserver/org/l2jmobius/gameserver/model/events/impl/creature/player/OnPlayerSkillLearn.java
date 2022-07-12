package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.enums.AcquireSkillType;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author UnAfraid
 */
public class OnPlayerSkillLearn implements IBaseEvent
{
	private final Npc _trainer;
	private final PlayerInstance _player;
	private final Skill _skill;
	private final AcquireSkillType _type;
	
	public OnPlayerSkillLearn(Npc trainer, PlayerInstance player, Skill skill, AcquireSkillType type)
	{
		_trainer = trainer;
		_player = player;
		_skill = skill;
		_type = type;
	}
	
	public Npc getTrainer()
	{
		return _trainer;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public Skill getSkill()
	{
		return _skill;
	}
	
	public AcquireSkillType getAcquireType()
	{
		return _type;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_SKILL_LEARN;
	}
}
