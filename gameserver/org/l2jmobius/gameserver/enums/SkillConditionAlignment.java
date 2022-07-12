package org.l2jmobius.gameserver.enums;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Sdw
 */
public enum SkillConditionAlignment
{
	LAWFUL
	{
		@Override
		public boolean test(PlayerInstance player)
		{
			return player.getReputation() >= 0;
		}
	},
	CHAOTIC
	{
		@Override
		public boolean test(PlayerInstance player)
		{
			return player.getReputation() < 0;
		}
	};
	
	public abstract boolean test(PlayerInstance player);
}
