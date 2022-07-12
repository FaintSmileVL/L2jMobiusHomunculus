package org.l2jmobius.gameserver.model.eventengine.conditions;

import java.util.Objects;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.eventengine.AbstractEventManager;
import org.l2jmobius.gameserver.model.eventengine.EventScheduler;
import org.l2jmobius.gameserver.model.eventengine.IConditionalEventScheduler;

/**
 * @author UnAfraid
 */
public class BetweenConditionalScheduler implements IConditionalEventScheduler
{
	private static final Logger LOGGER = Logger.getLogger(BetweenConditionalScheduler.class.getName());
	private final AbstractEventManager<?> _eventManager;
	private final String _name;
	private final String _scheduler1;
	private final String _scheduler2;
	
	public BetweenConditionalScheduler(AbstractEventManager<?> eventManager, String name, String scheduler1, String scheduler2)
	{
		Objects.requireNonNull(eventManager);
		Objects.requireNonNull(name);
		Objects.requireNonNull(scheduler1);
		Objects.requireNonNull(scheduler2);
		
		_eventManager = eventManager;
		_name = name;
		_scheduler1 = scheduler1;
		_scheduler2 = scheduler2;
	}
	
	@Override
	public boolean test()
	{
		final EventScheduler scheduler1 = _eventManager.getScheduler(_scheduler1);
		final EventScheduler scheduler2 = _eventManager.getScheduler(_scheduler2);
		if (scheduler1 == null)
		{
			throw new NullPointerException("Scheduler1 not found: " + _scheduler1);
		}
		else if (scheduler2 == null)
		{
			throw new NullPointerException("Scheduler2 not found: " + _scheduler2);
		}
		
		final long previousStart = scheduler1.getPrevSchedule();
		final long previousEnd = scheduler2.getPrevSchedule();
		return previousStart > previousEnd;
	}
	
	@Override
	public void run()
	{
		final EventScheduler mainScheduler = _eventManager.getScheduler(_name);
		if (mainScheduler == null)
		{
			throw new NullPointerException("Main scheduler not found: " + _name);
		}
		mainScheduler.run();
		LOGGER.info("Event " + _eventManager.getClass().getSimpleName() + " will resume because is within the event period.");
	}
}
