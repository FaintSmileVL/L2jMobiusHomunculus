package org.l2jmobius.gameserver.model.eventengine.conditions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.model.eventengine.AbstractEventManager;
import org.l2jmobius.gameserver.model.eventengine.EventScheduler;
import org.l2jmobius.gameserver.model.eventengine.IConditionalEventScheduler;

/**
 * @author UnAfraid
 */
public class HaventRunConditionalScheduler implements IConditionalEventScheduler
{
	private static final Logger LOGGER = Logger.getLogger(HaventRunConditionalScheduler.class.getName());
	private final AbstractEventManager<?> _eventManager;
	private final String _name;
	
	public HaventRunConditionalScheduler(AbstractEventManager<?> eventManager, String name)
	{
		_eventManager = eventManager;
		_name = name;
	}
	
	@Override
	public boolean test()
	{
		final EventScheduler mainScheduler = _eventManager.getScheduler(_name);
		if (mainScheduler == null)
		{
			throw new NullPointerException("Scheduler not found: " + _name);
		}
		
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT lastRun FROM event_schedulers WHERE eventName = ? AND schedulerName = ?"))
		{
			ps.setString(1, _eventManager.getName());
			ps.setString(2, mainScheduler.getName());
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					final long lastRun = rs.getTimestamp(1).getTime();
					final long lastPossibleRun = mainScheduler.getPrevSchedule();
					return (lastPossibleRun > lastRun) && (Math.abs(lastPossibleRun - lastRun) > 1000);
				}
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Failed to retreive information for scheduled task event manager: " + _eventManager.getClass().getSimpleName() + " scheduler: " + _name, e);
		}
		return false;
	}
	
	@Override
	public void run()
	{
		final EventScheduler mainScheduler = _eventManager.getScheduler(_name);
		if (mainScheduler == null)
		{
			throw new NullPointerException("Scheduler not found: " + _name);
		}
		
		if (mainScheduler.updateLastRun())
		{
			mainScheduler.run();
		}
	}
}
