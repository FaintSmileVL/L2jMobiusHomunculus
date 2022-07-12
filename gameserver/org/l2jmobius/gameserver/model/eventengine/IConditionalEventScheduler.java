package org.l2jmobius.gameserver.model.eventengine;

/**
 * @author UnAfraid
 */
public interface IConditionalEventScheduler
{
	boolean test();
	
	void run();
}
