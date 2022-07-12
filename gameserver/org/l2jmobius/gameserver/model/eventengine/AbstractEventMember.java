package org.l2jmobius.gameserver.model.eventengine;

import java.util.concurrent.atomic.AtomicInteger;

import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 * @param <T>
 */
public abstract class AbstractEventMember<T extends AbstractEvent<?>>
{
	private final int _objectId;
	private final T _event;
	private final AtomicInteger _score = new AtomicInteger();
	
	public AbstractEventMember(PlayerInstance player, T event)
	{
		_objectId = player.getObjectId();
		_event = event;
	}
	
	public int getObjectId()
	{
		return _objectId;
	}
	
	public PlayerInstance getPlayer()
	{
		return World.getInstance().getPlayer(_objectId);
	}
	
	public void sendPacket(IClientOutgoingPacket... packets)
	{
		final PlayerInstance player = getPlayer();
		if ((player != null) && player.isOnline() && !player.isInOfflineMode())
		{
			for (IClientOutgoingPacket packet : packets)
			{
				player.sendPacket(packet);
			}
		}
	}
	
	public int getClassId()
	{
		final PlayerInstance player = getPlayer();
		if (player != null)
		{
			return player.getClassId().getId();
		}
		return 0;
	}
	
	public void setScore(int score)
	{
		_score.set(score);
	}
	
	public int getScore()
	{
		return _score.get();
	}
	
	public int incrementScore()
	{
		return _score.incrementAndGet();
	}
	
	public int decrementScore()
	{
		return _score.decrementAndGet();
	}
	
	public int addScore(int score)
	{
		return _score.addAndGet(score);
	}
	
	public T getEvent()
	{
		return _event;
	}
}
