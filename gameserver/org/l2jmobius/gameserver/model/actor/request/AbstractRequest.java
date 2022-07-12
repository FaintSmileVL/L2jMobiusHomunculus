package org.l2jmobius.gameserver.model.actor.request;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public abstract class AbstractRequest
{
	private final PlayerInstance _player;
	private volatile long _timestamp = 0;
	private volatile boolean _isProcessing;
	private ScheduledFuture<?> _timeOutTask;
	
	public AbstractRequest(PlayerInstance player)
	{
		Objects.requireNonNull(player);
		_player = player;
	}
	
	public PlayerInstance getActiveChar()
	{
		return _player;
	}
	
	public long getTimestamp()
	{
		return _timestamp;
	}
	
	public void setTimestamp(long timestamp)
	{
		_timestamp = timestamp;
	}
	
	public void scheduleTimeout(long delay)
	{
		_timeOutTask = ThreadPool.schedule(this::onTimeout, delay);
	}
	
	public boolean isTimeout()
	{
		return (_timeOutTask != null) && !_timeOutTask.isDone();
	}
	
	public boolean isProcessing()
	{
		return _isProcessing;
	}
	
	public boolean setProcessing(boolean isProcessing)
	{
		return _isProcessing = isProcessing;
	}
	
	public boolean canWorkWith(AbstractRequest request)
	{
		return true;
	}
	
	public boolean isItemRequest()
	{
		return false;
	}
	
	public abstract boolean isUsing(int objectId);
	
	public void onTimeout()
	{
	}
}
