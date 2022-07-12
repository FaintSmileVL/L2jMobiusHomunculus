package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Target My Party Except Me condition implementation.
 * @author Adry_85
 */
public class ConditionTargetMyPartyExceptMe extends Condition
{
	private final boolean _value;
	
	public ConditionTargetMyPartyExceptMe(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		boolean isPartyMember = true;
		final PlayerInstance player = effector.getActingPlayer();
		if ((player == null) || (effected == null) || !effected.isPlayer())
		{
			isPartyMember = false;
		}
		else if (player == effected)
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_USE_THIS_ON_YOURSELF);
			isPartyMember = false;
		}
		else if (!player.isInParty() || !player.getParty().equals(effected.getParty()))
		{
			final SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			sm.addSkillName(skill);
			player.sendPacket(sm);
			isPartyMember = false;
		}
		return _value == isPartyMember;
	}
}
