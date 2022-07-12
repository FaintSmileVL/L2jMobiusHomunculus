package org.l2jmobius.gameserver.model.interfaces;

import org.l2jmobius.gameserver.enums.Position;
import org.l2jmobius.gameserver.util.Util;

/**
 * Object world location storage interface.
 * @author xban1x
 */
public interface ILocational
{
	/**
	 * Gets the X coordinate of this object.
	 * @return the X coordinate
	 */
	int getX();
	
	/**
	 * Gets the Y coordinate of this object.
	 * @return the current Y coordinate
	 */
	int getY();
	
	/**
	 * Gets the Z coordinate of this object.
	 * @return the current Z coordinate
	 */
	int getZ();
	
	/**
	 * Gets the heading of this object.
	 * @return the current heading
	 */
	int getHeading();
	
	/**
	 * Gets this object's location.
	 * @return a {@link ILocational} object containing the current position of this object
	 */
	ILocational getLocation();
	
	/**
	 * @param to
	 * @return the heading to the target specified
	 */
	default int calculateHeadingTo(ILocational to)
	{
		return Util.calculateHeadingFrom(getX(), getY(), to.getX(), to.getY());
	}
	
	/**
	 * @param target
	 * @return {@code true} if this location is in front of the target location based on the game's concept of position.
	 */
	default boolean isInFrontOf(ILocational target)
	{
		if (target == null)
		{
			return false;
		}
		
		return Position.FRONT == Position.getPosition(this, target);
	}
	
	/**
	 * @param target
	 * @return {@code true} if this location is in one of the sides of the target location based on the game's concept of position.
	 */
	default boolean isOnSideOf(ILocational target)
	{
		if (target == null)
		{
			return false;
		}
		
		return Position.SIDE == Position.getPosition(this, target);
	}
	
	/**
	 * @param target
	 * @return {@code true} if this location is behind the target location based on the game's concept of position.
	 */
	default boolean isBehind(ILocational target)
	{
		if (target == null)
		{
			return false;
		}
		
		return Position.BACK == Position.getPosition(this, target);
	}
}
