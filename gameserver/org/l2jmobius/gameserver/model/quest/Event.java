package org.l2jmobius.gameserver.model.quest;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Abstract event class.
 * @author JIV
 */
public abstract class Event extends Quest
{
	public Event()
	{
		super(-1);
	}
	
	public abstract boolean eventStart(PlayerInstance eventMaker);
	
	public abstract boolean eventStop();
	
	public abstract boolean eventBypass(PlayerInstance player, String bypass);
}