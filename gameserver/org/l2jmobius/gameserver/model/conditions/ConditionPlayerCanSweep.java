package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Checks Sweeper conditions:
 * <ul>
 * <li>Minimum checks, player not null, skill not null.</li>
 * <li>Checks if the target isn't null, is dead and spoiled.</li>
 * <li>Checks if the sweeper player is the target spoiler, or is in the spoiler party.</li>
 * <li>Checks if the corpse is too old.</li>
 * <li>Checks inventory limit and weight max load won't be exceed after sweep.</li>
 * </ul>
 * If two or more conditions aren't meet at the same time, one message per condition will be shown.
 * @author Zoey76
 */
public class ConditionPlayerCanSweep extends Condition
{
	private final boolean _value;
	
	public ConditionPlayerCanSweep(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		boolean canSweep = false;
		if (effector.getActingPlayer() != null)
		{
			final PlayerInstance sweeper = effector.getActingPlayer();
			if (skill != null)
			{
				for (WorldObject wo : skill.getTargetsAffected(sweeper, effected))
				{
					if ((wo != null) && wo.isAttackable())
					{
						final Attackable attackable = (Attackable) wo;
						if (attackable.isDead())
						{
							if (attackable.isSpoiled())
							{
								canSweep = attackable.checkSpoilOwner(sweeper, true);
								if (canSweep)
								{
									canSweep = !attackable.isOldCorpse(sweeper, Config.CORPSE_CONSUME_SKILL_ALLOWED_TIME_BEFORE_DECAY, true);
								}
								if (canSweep)
								{
									canSweep = sweeper.getInventory().checkInventorySlotsAndWeight(attackable.getSpoilLootItems(), true, true);
								}
							}
							else
							{
								sweeper.sendPacket(SystemMessageId.SWEEPER_FAILED_TARGET_NOT_SPOILED);
							}
						}
					}
				}
			}
		}
		return _value == canSweep;
	}
}
