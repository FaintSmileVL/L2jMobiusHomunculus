package org.l2jmobius.gameserver.model.zone;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.model.zone.type.PeaceZone;

/**
 * @author Nos
 */
public class ZoneRegion
{
	private final int _regionX;
	private final int _regionY;
	private final Map<Integer, ZoneType> _zones = new ConcurrentHashMap<>();
	
	public ZoneRegion(int regionX, int regionY)
	{
		_regionX = regionX;
		_regionY = regionY;
	}
	
	public Map<Integer, ZoneType> getZones()
	{
		return _zones;
	}
	
	public int getRegionX()
	{
		return _regionX;
	}
	
	public int getRegionY()
	{
		return _regionY;
	}
	
	public void revalidateZones(Creature creature)
	{
		// do NOT update the world region while the character is still in the process of teleporting
		// Once the teleport is COMPLETED, revalidation occurs safely, at that time.
		if (creature.isTeleporting())
		{
			return;
		}
		
		for (ZoneType z : _zones.values())
		{
			z.revalidateInZone(creature);
		}
	}
	
	public void removeFromZones(Creature creature)
	{
		for (ZoneType z : _zones.values())
		{
			z.removeCharacter(creature);
		}
	}
	
	public boolean checkEffectRangeInsidePeaceZone(Skill skill, int x, int y, int z)
	{
		final int range = skill.getEffectRange();
		final int up = y + range;
		final int down = y - range;
		final int left = x + range;
		final int right = x - range;
		for (ZoneType e : _zones.values())
		{
			if (e instanceof PeaceZone)
			{
				if (e.isInsideZone(x, up, z))
				{
					return false;
				}
				
				if (e.isInsideZone(x, down, z))
				{
					return false;
				}
				
				if (e.isInsideZone(left, y, z))
				{
					return false;
				}
				
				if (e.isInsideZone(right, y, z))
				{
					return false;
				}
				
				if (e.isInsideZone(x, y, z))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void onDeath(Creature creature)
	{
		for (ZoneType z : _zones.values())
		{
			if (z.isInsideZone(creature))
			{
				z.onDieInside(creature);
			}
		}
	}
	
	public void onRevive(Creature creature)
	{
		for (ZoneType z : _zones.values())
		{
			if (z.isInsideZone(creature))
			{
				z.onReviveInside(creature);
			}
		}
	}
}
