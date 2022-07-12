package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionPlayerSex.
 */
public class ConditionPlayerSex extends Condition
{
	// male 0 female 1
	private final int _sex;
	
	/**
	 * Instantiates a new condition player sex.
	 * @param sex the sex
	 */
	public ConditionPlayerSex(int sex)
	{
		_sex = sex;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		if (effector.getActingPlayer() == null)
		{
			return false;
		}
		return (effector.getActingPlayer().getAppearance().isFemale() ? 1 : 0) == _sex;
	}
}
