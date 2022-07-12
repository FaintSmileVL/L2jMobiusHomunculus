package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author Gnacik
 */
public class EventMonsterInstance extends MonsterInstance
{
	// Block offensive skills usage on event mobs
	// mainly for AoE skills, disallow kill many event mobs
	// with one skill
	public boolean block_skill_attack = false;
	
	// Event mobs should drop items to ground
	// but item pickup must be protected to killer
	// Todo: Some mobs need protect drop for spawner
	public boolean drop_on_ground = false;
	
	public EventMonsterInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.EventMobInstance);
	}
	
	public void eventSetBlockOffensiveSkills(boolean value)
	{
		block_skill_attack = value;
	}
	
	public void eventSetDropOnGround(boolean value)
	{
		drop_on_ground = value;
	}
	
	public boolean eventDropOnGround()
	{
		return drop_on_ground;
	}
	
	public boolean eventSkillAttackBlocked()
	{
		return block_skill_attack;
	}
}