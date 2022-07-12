package org.l2jmobius.gameserver.model.effects;

import java.util.concurrent.ScheduledFuture;

/**
 * Effect Task Info DTO.
 * @author Zoey76
 */
public class EffectTaskInfo
{
	private final EffectTickTask _effectTask;
	private final ScheduledFuture<?> _scheduledFuture;
	
	public EffectTaskInfo(EffectTickTask effectTask, ScheduledFuture<?> scheduledFuture)
	{
		_effectTask = effectTask;
		_scheduledFuture = scheduledFuture;
	}
	
	public EffectTickTask getEffectTask()
	{
		return _effectTask;
	}
	
	public ScheduledFuture<?> getScheduledFuture()
	{
		return _scheduledFuture;
	}
}
