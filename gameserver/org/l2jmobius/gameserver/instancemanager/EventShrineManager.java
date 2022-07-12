package org.l2jmobius.gameserver.instancemanager;

/**
 * @author Mobius
 */
public class EventShrineManager
{
	private static boolean ENABLE_SHRINES = false;
	
	public boolean areShrinesEnabled()
	{
		return ENABLE_SHRINES;
	}
	
	public void setEnabled(boolean enabled)
	{
		ENABLE_SHRINES = enabled;
	}
	
	public static EventShrineManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final EventShrineManager INSTANCE = new EventShrineManager();
	}
}
