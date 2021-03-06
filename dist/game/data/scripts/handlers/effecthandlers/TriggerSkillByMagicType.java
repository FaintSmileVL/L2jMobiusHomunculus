/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.effecthandlers;

import java.util.logging.Level;

import util.CommonUtil;
import util.Rnd;
import org.l2jmobius.gameserver.data.xml.SkillData;
import org.l2jmobius.gameserver.handler.TargetHandler;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.effects.AbstractEffect;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.creature.OnCreatureSkillFinishCast;
import org.l2jmobius.gameserver.model.events.listeners.ConsumerEventListener;
import org.l2jmobius.gameserver.model.holders.SkillHolder;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.skills.SkillCaster;
import org.l2jmobius.gameserver.model.skills.targets.TargetType;

/**
 * Trigger skill by isMagic type.
 * @author Nik
 */
public class TriggerSkillByMagicType extends AbstractEffect
{
	private final int[] _magicTypes;
	private final int _chance;
	private final int _skillLevelScaleTo;
	private final SkillHolder _skill;
	private final TargetType _targetType;
	
	/**
	 * @param params
	 */
	
	public TriggerSkillByMagicType(StatSet params)
	{
		_chance = params.getInt("chance", 100);
		_magicTypes = params.getIntArray("magicTypes", ";");
		_skill = new SkillHolder(params.getInt("skillId", 0), params.getInt("skillLevel", 0));
		_skillLevelScaleTo = params.getInt("skillLevelScaleTo", 0);
		_targetType = params.getEnum("targetType", TargetType.class, TargetType.TARGET);
	}
	
	private void onSkillUseEvent(OnCreatureSkillFinishCast event)
	{
		if (!event.getTarget().isCreature())
		{
			return;
		}
		
		if (!CommonUtil.contains(_magicTypes, event.getSkill().getMagicType()))
		{
			return;
		}
		
		if ((_chance < 100) && (Rnd.get(100) > _chance))
		{
			return;
		}
		
		final Skill triggerSkill;
		if (_skillLevelScaleTo <= 0)
		{
			triggerSkill = _skill.getSkill();
		}
		else
		{
			final BuffInfo buffInfo = ((Creature) event.getTarget()).getEffectList().getBuffInfoBySkillId(_skill.getSkillId());
			if (buffInfo != null)
			{
				triggerSkill = SkillData.getInstance().getSkill(_skill.getSkillId(), Math.min(_skillLevelScaleTo, buffInfo.getSkill().getLevel() + 1));
			}
			else
			{
				triggerSkill = _skill.getSkill();
			}
		}
		
		WorldObject target = null;
		try
		{
			target = TargetHandler.getInstance().getHandler(_targetType).getTarget(event.getCaster(), event.getTarget(), triggerSkill, false, false, false);
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "Exception in ITargetTypeHandler.getTarget(): " + e.getMessage(), e);
		}
		
		if ((target != null) && target.isCreature())
		{
			SkillCaster.triggerCast(event.getCaster(), (Creature) target, triggerSkill);
		}
	}
	
	@Override
	public void onStart(Creature effector, Creature effected, Skill skill, ItemInstance item)
	{
		if ((_chance == 0) || (_skill.getSkillId() == 0) || (_skill.getSkillLevel() == 0) || (_magicTypes.length == 0))
		{
			return;
		}
		
		effected.addListener(new ConsumerEventListener(effected, EventType.ON_CREATURE_SKILL_FINISH_CAST, (OnCreatureSkillFinishCast event) -> onSkillUseEvent(event), this));
	}
	
	@Override
	public void onExit(Creature effector, Creature effected, Skill skill)
	{
		effected.removeListenerIf(EventType.ON_CREATURE_SKILL_FINISH_CAST, listener -> listener.getOwner() == this);
	}
}
