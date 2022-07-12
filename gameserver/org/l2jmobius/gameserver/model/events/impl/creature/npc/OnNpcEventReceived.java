package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnNpcEventReceived implements IBaseEvent
{
	private final String _eventName;
	private final Npc _sender;
	private final Npc _receiver;
	private final WorldObject _reference;
	
	public OnNpcEventReceived(String eventName, Npc sender, Npc receiver, WorldObject reference)
	{
		_eventName = eventName;
		_sender = sender;
		_receiver = receiver;
		_reference = reference;
	}
	
	public String getEventName()
	{
		return _eventName;
	}
	
	public Npc getSender()
	{
		return _sender;
	}
	
	public Npc getReceiver()
	{
		return _receiver;
	}
	
	public WorldObject getReference()
	{
		return _reference;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_EVENT_RECEIVED;
	}
}
