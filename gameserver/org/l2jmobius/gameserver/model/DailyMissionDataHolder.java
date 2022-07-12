package org.l2jmobius.gameserver.model;

import java.util.List;
import java.util.function.Function;

import org.l2jmobius.gameserver.enums.ClassId;
import org.l2jmobius.gameserver.enums.DailyMissionStatus;
import org.l2jmobius.gameserver.handler.AbstractDailyMissionHandler;
import org.l2jmobius.gameserver.handler.DailyMissionHandler;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author Sdw
 */
public class DailyMissionDataHolder
{
	private final int _id;
	private final List<ItemHolder> _rewardsItems;
	private final List<ClassId> _classRestriction;
	private final int _requiredCompletions;
	private final StatSet _params;
	private final boolean _dailyReset;
	private final boolean _isOneTime;
	private final boolean _isMainClassOnly;
	private final boolean _isDualClassOnly;
	private final boolean _isDisplayedWhenNotAvailable;
	private final AbstractDailyMissionHandler _handler;
	
	public DailyMissionDataHolder(StatSet set)
	{
		final Function<DailyMissionDataHolder, AbstractDailyMissionHandler> handler = DailyMissionHandler.getInstance().getHandler(set.getString("handler"));
		_id = set.getInt("id");
		_requiredCompletions = set.getInt("requiredCompletion", 0);
		_rewardsItems = set.getList("items", ItemHolder.class);
		_classRestriction = set.getList("classRestriction", ClassId.class);
		_params = set.getObject("params", StatSet.class);
		_dailyReset = set.getBoolean("dailyReset", true);
		_isOneTime = set.getBoolean("isOneTime", true);
		_isMainClassOnly = set.getBoolean("isMainClassOnly", true);
		_isDualClassOnly = set.getBoolean("isDualClassOnly", false);
		_isDisplayedWhenNotAvailable = set.getBoolean("isDisplayedWhenNotAvailable", true);
		_handler = handler != null ? handler.apply(this) : null;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public List<ClassId> getClassRestriction()
	{
		return _classRestriction;
	}
	
	public List<ItemHolder> getRewards()
	{
		return _rewardsItems;
	}
	
	public int getRequiredCompletions()
	{
		return _requiredCompletions;
	}
	
	public StatSet getParams()
	{
		return _params;
	}
	
	public boolean dailyReset()
	{
		return _dailyReset;
	}
	
	public boolean isOneTime()
	{
		return _isOneTime;
	}
	
	public boolean isMainClassOnly()
	{
		return _isMainClassOnly;
	}
	
	public boolean isDualClassOnly()
	{
		return _isDualClassOnly;
	}
	
	public boolean isDisplayedWhenNotAvailable()
	{
		return _isDisplayedWhenNotAvailable;
	}
	
	public boolean isDisplayable(PlayerInstance player)
	{
		// Check if its main class only
		if (_isMainClassOnly && (player.isSubClassActive() || player.isDualClassActive()))
		{
			return false;
		}
		
		// Check if its dual class only.
		if (_isDualClassOnly && !player.isDualClassActive())
		{
			return false;
		}
		
		// Check for specific class restrictions
		if (!_classRestriction.isEmpty() && !_classRestriction.contains(player.getClassId()))
		{
			return false;
		}
		
		final int status = getStatus(player);
		if (!_isDisplayedWhenNotAvailable && (status == DailyMissionStatus.NOT_AVAILABLE.getClientId()))
		{
			return false;
		}
		
		// Show only if its repeatable, recently completed or incompleted that has met the checks above.
		return (!_isOneTime || getRecentlyCompleted(player) || (status != DailyMissionStatus.COMPLETED.getClientId()));
	}
	
	public void requestReward(PlayerInstance player)
	{
		if ((_handler != null) && isDisplayable(player))
		{
			_handler.requestReward(player);
		}
	}
	
	public int getStatus(PlayerInstance player)
	{
		return _handler != null ? _handler.getStatus(player) : DailyMissionStatus.NOT_AVAILABLE.getClientId();
	}
	
	public int getProgress(PlayerInstance player)
	{
		return _handler != null ? _handler.getProgress(player) : DailyMissionStatus.NOT_AVAILABLE.getClientId();
	}
	
	public boolean getRecentlyCompleted(PlayerInstance player)
	{
		return (_handler != null) && _handler.getRecentlyCompleted(player);
	}
	
	public boolean isLevelUpMission()
	{
		return (_handler != null) && _handler.isLevelUpMission();
	}
	
	public void reset()
	{
		if (_handler != null)
		{
			_handler.reset();
		}
	}
}
