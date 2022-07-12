package org.l2jmobius.gameserver.model;

import java.util.concurrent.ScheduledFuture;

import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author DrHouse
 */
public class DropProtection implements Runnable
{
	private volatile boolean _isProtected = false;
	private Creature _owner = null;
	private ScheduledFuture<?> _task = null;
	
	private static final long PROTECTED_MILLIS_TIME = 15000;
	
	@Override
	public synchronized void run()
	{
		_isProtected = false;
		_owner = null;
		_task = null;
	}
	
	public boolean isProtected()
	{
		return _isProtected;
	}
	
	public Creature getOwner()
	{
		return _owner;
	}
	
	public synchronized boolean tryPickUp(PlayerInstance actor)
	{
		if (!_isProtected)
		{
			return true;
		}
		
		if (_owner == actor)
		{
			return true;
		}
		
		if ((_owner.getParty() != null) && (_owner.getParty() == actor.getParty()))
		{
			return true;
		}
		
		/*
		 * if (_owner.getClan() != null && _owner.getClan() == actor.getClan()) return true;
		 */
		
		return false;
	}
	
	public boolean tryPickUp(PetInstance pet)
	{
		return tryPickUp(pet.getOwner());
	}
	
	public synchronized void unprotect()
	{
		if (_task != null)
		{
			_task.cancel(false);
		}
		_isProtected = false;
		_owner = null;
		_task = null;
	}
	
	public synchronized void protect(Creature creature)
	{
		unprotect();
		
		_isProtected = true;
		_owner = creature;
		if (_owner == null)
		{
			throw new NullPointerException("Trying to protect dropped item to null owner");
		}
		
		_task = ThreadPool.schedule(this, PROTECTED_MILLIS_TIME);
	}
}
