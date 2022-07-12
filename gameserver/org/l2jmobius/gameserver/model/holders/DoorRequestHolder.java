package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;

/**
 * @author UnAfraid
 */
public class DoorRequestHolder
{
	private final DoorInstance _target;
	
	public DoorRequestHolder(DoorInstance door)
	{
		_target = door;
	}
	
	public DoorInstance getDoor()
	{
		return _target;
	}
}
