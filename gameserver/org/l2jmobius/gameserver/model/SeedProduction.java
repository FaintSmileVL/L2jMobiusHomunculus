package org.l2jmobius.gameserver.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xban1x
 */
public class SeedProduction
{
	private final int _seedId;
	private final long _price;
	private final long _startAmount;
	private final AtomicLong _amount;
	
	public SeedProduction(int id, long amount, long price, long startAmount)
	{
		_seedId = id;
		_amount = new AtomicLong(amount);
		_price = price;
		_startAmount = startAmount;
	}
	
	public int getId()
	{
		return _seedId;
	}
	
	public long getAmount()
	{
		return _amount.get();
	}
	
	public long getPrice()
	{
		return _price;
	}
	
	public long getStartAmount()
	{
		return _startAmount;
	}
	
	public void setAmount(long amount)
	{
		_amount.set(amount);
	}
	
	public boolean decreaseAmount(long value)
	{
		long current;
		long next;
		do
		{
			current = _amount.get();
			next = current - value;
			if (next < 0)
			{
				return false;
			}
		}
		while (!_amount.compareAndSet(current, next));
		return true;
	}
}