package org.l2jmobius.gameserver.model.holders;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mobius
 */
public class AutoPlaySettingsHolder
{
	private final AtomicBoolean _pickup = new AtomicBoolean();
	private final AtomicBoolean _longRange = new AtomicBoolean();
	private final AtomicBoolean _respectfulHunting = new AtomicBoolean();
	private final AtomicInteger _autoPotionPercent = new AtomicInteger();
	
	public AutoPlaySettingsHolder()
	{
	}
	
	public boolean doPickup()
	{
		return _pickup.get();
	}
	
	public void setPickup(boolean value)
	{
		_pickup.set(value);
	}
	
	public boolean isLongRange()
	{
		return _longRange.get();
	}
	
	public void setLongRange(boolean value)
	{
		_longRange.set(value);
	}
	
	public boolean isRespectfulHunting()
	{
		return _respectfulHunting.get();
	}
	
	public void setRespectfulHunting(boolean value)
	{
		_respectfulHunting.set(value);
	}
	
	public int getAutoPotionPercent()
	{
		return _autoPotionPercent.get();
	}
	
	public void setAutoPotionPercent(int value)
	{
		_autoPotionPercent.set(value);
	}
}
