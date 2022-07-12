package org.l2jmobius.gameserver.model.actor.instance;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.Spawn;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Tower;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * Class for Control Tower instance.
 */
public class ControlTowerInstance extends Tower
{
	private Set<Spawn> _guards;
	
	public ControlTowerInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.ControlTowerInstance);
	}
	
	@Override
	public boolean doDie(Creature killer)
	{
		if (getCastle().getSiege().isInProgress())
		{
			getCastle().getSiege().killedCT(this);
			
			if ((_guards != null) && !_guards.isEmpty())
			{
				for (Spawn spawn : _guards)
				{
					if (spawn == null)
					{
						continue;
					}
					try
					{
						spawn.stopRespawn();
						// spawn.getLastSpawn().doDie(spawn.getLastSpawn());
					}
					catch (Exception e)
					{
						LOGGER.log(Level.WARNING, "Error at ControlTowerInstance", e);
					}
				}
				_guards.clear();
			}
		}
		return super.doDie(killer);
	}
	
	public void registerGuard(Spawn guard)
	{
		getGuards().add(guard);
	}
	
	private Set<Spawn> getGuards()
	{
		if (_guards == null)
		{
			synchronized (this)
			{
				if (_guards == null)
				{
					_guards = ConcurrentHashMap.newKeySet();
				}
			}
		}
		return _guards;
	}
}
