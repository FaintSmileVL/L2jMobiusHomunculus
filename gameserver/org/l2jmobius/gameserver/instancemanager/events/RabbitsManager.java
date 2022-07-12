package org.l2jmobius.gameserver.instancemanager.events;

import org.l2jmobius.gameserver.instancemanager.QuestManager;
import org.l2jmobius.gameserver.model.eventengine.AbstractEvent;
import org.l2jmobius.gameserver.model.eventengine.AbstractEventManager;
import org.l2jmobius.gameserver.model.eventengine.ScheduleTarget;
import org.l2jmobius.gameserver.model.quest.Event;

/**
 * @author Mobius
 */
public class RabbitsManager extends AbstractEventManager<AbstractEvent<?>>
{
	protected RabbitsManager()
	{
	}
	
	@Override
	public void onInitialized()
	{
	}
	
	@ScheduleTarget
	protected void startEvent()
	{
		final Event event = (Event) QuestManager.getInstance().getQuest("Rabbits");
		if (event != null)
		{
			event.eventStart(null);
		}
	}
	
	public static RabbitsManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final RabbitsManager INSTANCE = new RabbitsManager();
	}
}
