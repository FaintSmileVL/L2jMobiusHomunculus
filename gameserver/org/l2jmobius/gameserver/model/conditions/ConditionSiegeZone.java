package org.l2jmobius.gameserver.model.conditions;

import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.instancemanager.FortManager;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.model.siege.Fort;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * The Class ConditionSiegeZone.
 * @author Gigiikun
 */
public class ConditionSiegeZone extends Condition
{
	// conditional values
	public static final int COND_NOT_ZONE = 0x0001;
	public static final int COND_CAST_ATTACK = 0x0002;
	public static final int COND_CAST_DEFEND = 0x0004;
	public static final int COND_CAST_NEUTRAL = 0x0008;
	public static final int COND_FORT_ATTACK = 0x0010;
	public static final int COND_FORT_DEFEND = 0x0020;
	public static final int COND_FORT_NEUTRAL = 0x0040;
	
	private final int _value;
	private final boolean _self;
	
	/**
	 * Instantiates a new condition siege zone.
	 * @param value the value
	 * @param self the self
	 */
	public ConditionSiegeZone(int value, boolean self)
	{
		_value = value;
		_self = self;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, Item item)
	{
		final Creature target = _self ? effector : effected;
		final Castle castle = CastleManager.getInstance().getCastle(target);
		final Fort fort = FortManager.getInstance().getFort(target);
		if ((castle == null) && (fort == null))
		{
			return (_value & COND_NOT_ZONE) != 0;
		}
		if (castle != null)
		{
			return checkIfOk(target, castle, _value);
		}
		return checkIfOk(target, fort, _value);
	}
	
	/**
	 * Check if ok.
	 * @param creature the creature
	 * @param castle the castle
	 * @param value the value
	 * @return true, if successful
	 */
	public static boolean checkIfOk(Creature creature, Castle castle, int value)
	{
		if ((creature == null) || !creature.isPlayer())
		{
			return false;
		}
		
		final PlayerInstance player = (PlayerInstance) creature;
		if (((castle == null) || (castle.getResidenceId() <= 0)))
		{
			if ((value & COND_NOT_ZONE) != 0)
			{
				return true;
			}
		}
		else if (!castle.getZone().isActive())
		{
			if ((value & COND_NOT_ZONE) != 0)
			{
				return true;
			}
		}
		else if (((value & COND_CAST_ATTACK) != 0) && player.isRegisteredOnThisSiegeField(castle.getResidenceId()) && (player.getSiegeState() == 1))
		{
			return true;
		}
		else if (((value & COND_CAST_DEFEND) != 0) && player.isRegisteredOnThisSiegeField(castle.getResidenceId()) && (player.getSiegeState() == 2))
		{
			return true;
		}
		else if (((value & COND_CAST_NEUTRAL) != 0) && (player.getSiegeState() == 0))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check if ok.
	 * @param creature the creature
	 * @param fort the fort
	 * @param value the value
	 * @return true, if successful
	 */
	public static boolean checkIfOk(Creature creature, Fort fort, int value)
	{
		if ((creature == null) || !creature.isPlayer())
		{
			return false;
		}
		
		final PlayerInstance player = (PlayerInstance) creature;
		if (((fort == null) || (fort.getResidenceId() <= 0)))
		{
			if ((value & COND_NOT_ZONE) != 0)
			{
				return true;
			}
		}
		else if (!fort.getZone().isActive())
		{
			if ((value & COND_NOT_ZONE) != 0)
			{
				return true;
			}
		}
		else if (((value & COND_FORT_ATTACK) != 0) && player.isRegisteredOnThisSiegeField(fort.getResidenceId()) && (player.getSiegeState() == 1))
		{
			return true;
		}
		else if (((value & COND_FORT_DEFEND) != 0) && player.isRegisteredOnThisSiegeField(fort.getResidenceId()) && (player.getSiegeState() == 2))
		{
			return true;
		}
		else if (((value & COND_FORT_NEUTRAL) != 0) && (player.getSiegeState() == 0))
		{
			return true;
		}
		
		return false;
	}
}
