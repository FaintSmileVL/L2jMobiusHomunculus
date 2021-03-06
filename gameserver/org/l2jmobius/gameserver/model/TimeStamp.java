package org.l2jmobius.gameserver.model;

import util.Chronos;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * Simple class containing all necessary information to maintain<br>
 * valid time stamps and reuse for skills and items reuse upon re-login.<br>
 * <b>Filter this carefully as it becomes redundant to store reuse for small delays.</b>
 * @author Yesod, Zoey76, Mobius
 */
public class TimeStamp
{
	/** Item or skill ID. */
	private final int _id1;
	/** Item object ID or skill level. */
	private final int _id2;
	/** Skill level. */
	private final int _id3;
	/** Item or skill reuse time. */
	private final long _reuse;
	/** Time stamp. */
	private volatile long _stamp;
	/** Shared reuse group. */
	private final int _group;
	
	/**
	 * Skill time stamp constructor.
	 * @param skill the skill upon the stamp will be created.
	 * @param reuse the reuse time for this skill.
	 * @param systime overrides the system time with a customized one.
	 */
	public TimeStamp(Skill skill, long reuse, long systime)
	{
		_id1 = skill.getId();
		_id2 = skill.getLevel();
		_id3 = skill.getSubLevel();
		_reuse = reuse;
		_stamp = systime > 0 ? systime : reuse != 0 ? Chronos.currentTimeMillis() + reuse : 0;
		_group = -1;
	}
	
	/**
	 * Item time stamp constructor.
	 * @param item the item upon the stamp will be created.
	 * @param reuse the reuse time for this item.
	 * @param systime overrides the system time with a customized one.
	 */
	public TimeStamp(ItemInstance item, long reuse, long systime)
	{
		_id1 = item.getId();
		_id2 = item.getObjectId();
		_id3 = 0;
		_reuse = reuse;
		_stamp = systime > 0 ? systime : reuse != 0 ? Chronos.currentTimeMillis() + reuse : 0;
		_group = item.getSharedReuseGroup();
	}
	
	/**
	 * Gets the time stamp.
	 * @return the time stamp, either the system time where this time stamp was created or the custom time assigned
	 */
	public long getStamp()
	{
		return _stamp;
	}
	
	/**
	 * Gets the item ID.
	 * @return the item ID
	 */
	public int getItemId()
	{
		return _id1;
	}
	
	/**
	 * Gets the item object ID.
	 * @return the item object ID
	 */
	public int getItemObjectId()
	{
		return _id2;
	}
	
	/**
	 * Gets the skill ID.
	 * @return the skill ID
	 */
	public int getSkillId()
	{
		return _id1;
	}
	
	/**
	 * Gets the skill level.
	 * @return the skill level
	 */
	public int getSkillLevel()
	{
		return _id2;
	}
	
	/**
	 * Gets the skill sub level.
	 * @return the skill level
	 */
	public int getSkillSubLevel()
	{
		return _id3;
	}
	
	/**
	 * Gets the reuse.
	 * @return the reuse
	 */
	public long getReuse()
	{
		return _reuse;
	}
	
	/**
	 * Get the shared reuse group.<br>
	 * Only used on items.
	 * @return the shared reuse group
	 */
	public int getSharedReuseGroup()
	{
		return _group;
	}
	
	/**
	 * Gets the remaining time.
	 * @return the remaining time for this time stamp to expire
	 */
	public long getRemaining()
	{
		if (_stamp == 0)
		{
			return 0;
		}
		final long remainingTime = Math.max(_stamp - Chronos.currentTimeMillis(), 0);
		if (remainingTime == 0)
		{
			_stamp = 0;
		}
		return remainingTime;
	}
	
	/**
	 * Verifies if the reuse delay has passed.
	 * @return {@code true} if this time stamp has expired, {@code false} otherwise
	 */
	public boolean hasNotPassed()
	{
		if (_stamp == 0)
		{
			return false;
		}
		final boolean hasNotPassed = Chronos.currentTimeMillis() < _stamp;
		if (!hasNotPassed)
		{
			_stamp = 0;
		}
		return hasNotPassed;
	}
}
