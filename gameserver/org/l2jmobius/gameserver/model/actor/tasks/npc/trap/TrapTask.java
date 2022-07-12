package org.l2jmobius.gameserver.model.actor.tasks.npc.trap;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.actor.instance.TrapInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.serverpackets.SocialAction;

/**
 * Trap task.
 * @author Zoey76
 */
public class TrapTask implements Runnable
{
	private static final Logger LOGGER = Logger.getLogger(TrapTask.class.getName());
	private static final int TICK = 1000; // 1s
	private final TrapInstance _trap;
	
	public TrapTask(TrapInstance trap)
	{
		_trap = trap;
	}
	
	@Override
	public void run()
	{
		try
		{
			if (!_trap.isTriggered())
			{
				if (_trap.hasLifeTime())
				{
					_trap.setRemainingTime(_trap.getRemainingTime() - TICK);
					if (_trap.getRemainingTime() < (_trap.getLifeTime() - 15000))
					{
						_trap.broadcastPacket(new SocialAction(_trap.getObjectId(), 2));
					}
					if (_trap.getRemainingTime() <= 0)
					{
						_trap.triggerTrap(_trap);
						return;
					}
				}
				
				final Skill skill = _trap.getSkill();
				if ((skill != null) && !skill.getTargetsAffected(_trap, _trap).isEmpty())
				{
					_trap.triggerTrap(_trap);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.severe(TrapTask.class.getSimpleName() + ": " + e.getMessage());
			_trap.unSummon();
		}
	}
}
