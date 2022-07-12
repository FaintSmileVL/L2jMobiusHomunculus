package org.l2jmobius.gameserver.model;

import java.util.logging.Level;

import util.Rnd;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.ControllableMobInstance;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author littlecrow A special spawn implementation to spawn controllable mob
 */
public class GroupSpawn extends Spawn
{
	private final NpcTemplate _template;
	
	public GroupSpawn(NpcTemplate mobTemplate) throws ClassNotFoundException, NoSuchMethodException
	{
		super(mobTemplate);
		_template = mobTemplate;
		setAmount(1);
	}
	
	public Npc doGroupSpawn()
	{
		try
		{
			if (_template.isType("Pet") || _template.isType("Minion"))
			{
				return null;
			}
			
			int newlocx = 0;
			int newlocy = 0;
			int newlocz = 0;
			if ((getX() == 0) && (getY() == 0))
			{
				if (getLocationId() == 0)
				{
					return null;
				}
				
				return null;
			}
			
			newlocx = getX();
			newlocy = getY();
			newlocz = getZ();
			
			final Npc mob = new ControllableMobInstance(_template);
			mob.setCurrentHpMp(mob.getMaxHp(), mob.getMaxMp());
			mob.setHeading(getHeading() == -1 ? Rnd.get(61794) : getHeading());
			mob.setSpawn(this);
			mob.spawnMe(newlocx, newlocy, newlocz);
			return mob;
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "NPC class not found: " + e.getMessage(), e);
			return null;
		}
	}
}