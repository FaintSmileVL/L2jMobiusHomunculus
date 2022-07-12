package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.ceremonyofchaos.CeremonyOfChaosEvent;
import org.l2jmobius.gameserver.model.effects.EffectFlag;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.taskmanager.AttackStanceTaskManager;

/**
 * @author Sdw
 */
public class ConditionPlayerCanSwitchSubclass extends Condition
{
	private final int _subIndex;
	
	public ConditionPlayerCanSwitchSubclass(int subIndex)
	{
		_subIndex = subIndex;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		boolean canSwitchSub = true;
		
		final PlayerInstance player = effector.getActingPlayer();
		if ((player == null) || player.isAlikeDead())
		{
			canSwitchSub = false;
		}
		else if (((_subIndex != 0) && (player.getSubClasses().get(_subIndex) == null)) || (player.getClassIndex() == _subIndex))
		{
			canSwitchSub = false;
		}
		else if (!player.isInventoryUnder90(true))
		{
			player.sendPacket(SystemMessageId.A_SUBCLASS_CANNOT_BE_CREATED_OR_CHANGED_BECAUSE_YOU_HAVE_EXCEEDED_YOUR_INVENTORY_LIMIT);
			canSwitchSub = false;
		}
		else if (player.getWeightPenalty() >= 2)
		{
			player.sendPacket(SystemMessageId.A_SUBCLASS_CANNOT_BE_CREATED_OR_CHANGED_WHILE_YOU_ARE_OVER_YOUR_WEIGHT_LIMIT);
			canSwitchSub = false;
		}
		else if (player.isOnEvent(CeremonyOfChaosEvent.class))
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_CHANGE_YOUR_SUBCLASS_WHILE_REGISTERED_IN_THE_CEREMONY_OF_CHAOS);
			canSwitchSub = false;
		}
		else if (player.isOnEvent())
		{
			player.sendMessage("You cannot change your subclass while registered in an event.");
			canSwitchSub = false;
		}
		else if (player.isAllSkillsDisabled())
		{
			canSwitchSub = false;
		}
		else if (player.isAffected(EffectFlag.MUTED))
		{
			canSwitchSub = false;
			player.sendPacket(SystemMessageId.YOU_CANNOT_CHANGE_THE_CLASS_BECAUSE_OF_IDENTITY_CRISIS);
		}
		else if (AttackStanceTaskManager.getInstance().hasAttackStanceTask(player) || (player.getPvpFlag() > 0) || player.isInInstance() || player.isTransformed() || player.isMounted())
		{
			canSwitchSub = false;
		}
		return canSwitchSub;
	}
}
