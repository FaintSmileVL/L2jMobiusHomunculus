package org.l2jmobius.gameserver.model.zone.type;

import java.util.concurrent.ScheduledFuture;

import org.l2jmobius.gameserver.ThreadPool;
import util.Rnd;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Teleport residence zone for clan hall sieges
 * @author BiggBoss
 */
public class ResidenceHallTeleportZone extends ResidenceTeleportZone
{
	private int _id;
	private ScheduledFuture<?> _teleTask;
	
	/**
	 * @param id
	 */
	public ResidenceHallTeleportZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals("residenceZoneId"))
		{
			_id = Integer.parseInt(value);
		}
		else
		{
			super.setParameter(name, value);
		}
	}
	
	public int getResidenceZoneId()
	{
		return _id;
	}
	
	public synchronized void checkTeleportTask()
	{
		if ((_teleTask == null) || _teleTask.isDone())
		{
			_teleTask = ThreadPool.schedule(new TeleportTask(), 30000);
		}
	}
	
	protected class TeleportTask implements Runnable
	{
		@Override
		public void run()
		{
			final int index = getSpawns().size() > 1 ? Rnd.get(getSpawns().size()) : 0;
			final Location loc = getSpawns().get(index);
			if (loc == null)
			{
				throw new NullPointerException();
			}
			
			for (PlayerInstance pc : getPlayersInside())
			{
				if (pc != null)
				{
					pc.teleToLocation(loc, false);
				}
			}
		}
	}
}
