package org.l2jmobius.gameserver.model.holders;

import java.time.Duration;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.interfaces.IIdentifiable;

/**
 * This class hold info needed for minions spawns<br>
 * @author Zealar
 */
public class MinionHolder implements IIdentifiable
{
	private final int _id;
	private final int _count;
	private final long _respawnTime;
	private final int _weightPoint;
	
	public MinionHolder(StatSet set)
	{
		_id = set.getInt("id");
		_count = set.getInt("count", 1);
		_respawnTime = set.getDuration("respawnTime", Duration.ofSeconds(0)).getSeconds() * 1000;
		_weightPoint = set.getInt("weightPoint", 0);
	}
	
	/**
	 * Constructs a minion holder.
	 * @param id the id
	 * @param count the count
	 * @param respawnTime the respawn time
	 * @param weightPoint the weight point
	 */
	public MinionHolder(int id, int count, long respawnTime, int weightPoint)
	{
		_id = id;
		_count = count;
		_respawnTime = respawnTime;
		_weightPoint = weightPoint;
	}
	
	/**
	 * @return the Identifier of the Minion to spawn.
	 */
	@Override
	public int getId()
	{
		return _id;
	}
	
	/**
	 * @return the count of the Minions to spawn.
	 */
	public int getCount()
	{
		return _count;
	}
	
	/**
	 * @return the respawn time of the Minions.
	 */
	public long getRespawnTime()
	{
		return _respawnTime;
	}
	
	/**
	 * @return the weight point of the Minion.
	 */
	public int getWeightPoint()
	{
		return _weightPoint;
	}
}
