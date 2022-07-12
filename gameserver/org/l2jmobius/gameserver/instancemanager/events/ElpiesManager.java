package org.l2jmobius.gameserver.instancemanager.events;

import org.l2jmobius.gameserver.instancemanager.QuestManager;
import org.l2jmobius.gameserver.model.eventengine.AbstractEvent;
import org.l2jmobius.gameserver.model.eventengine.AbstractEventManager;
import org.l2jmobius.gameserver.model.eventengine.ScheduleTarget;
import org.l2jmobius.gameserver.model.quest.Event;

/**
 * @author Mobius
 */
public class ElpiesManager extends AbstractEventManager<AbstractEvent<?>>
{
	protected ElpiesManager()
	{
	}
	
	@Override
	public void onInitialized()
	{
	}
	
	@ScheduleTarget
	protected void startEvent()
	{
		final Event event = (Event) QuestManager.getInstance().getQuest("Elpies");
		if (event != null)
		{
			event.eventStart(null);
		}
	}
	
	public static ElpiesManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ElpiesManager INSTANCE = new ElpiesManager();
	}
}
