package org.l2jmobius.gameserver.model.buylist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.data.DatabaseFactory;
import util.Chronos;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.type.EtcItemType;
import org.l2jmobius.gameserver.taskmanager.BuyListTaskManager;

/**
 * @author NosBit
 */
public class Product
{
	private static final Logger LOGGER = Logger.getLogger(Product.class.getName());
	
	private final int _buyListId;
	private final Item _item;
	private final long _price;
	private final long _restockDelay;
	private final long _maxCount;
	private final double _baseTax;
	private AtomicLong _count = null;
	
	public Product(int buyListId, Item item, long price, long restockDelay, long maxCount, int baseTax)
	{
		Objects.requireNonNull(item);
		_buyListId = buyListId;
		_item = item;
		_price = (price < 0) ? item.getReferencePrice() : price;
		_restockDelay = restockDelay * 60000;
		_maxCount = maxCount;
		_baseTax = baseTax / 100.0;
		if (hasLimitedStock())
		{
			_count = new AtomicLong(maxCount);
		}
	}
	
	public Item getItem()
	{
		return _item;
	}
	
	public int getItemId()
	{
		return _item.getId();
	}
	
	public long getPrice()
	{
		long price = _price;
		if (_item.getItemType().equals(EtcItemType.CASTLE_GUARD))
		{
			price *= Config.RATE_SIEGE_GUARDS_PRICE;
		}
		return price;
	}
	
	public double getBaseTaxRate()
	{
		return _baseTax;
	}
	
	public long getRestockDelay()
	{
		return _restockDelay;
	}
	
	public long getMaxCount()
	{
		return _maxCount;
	}
	
	public long getCount()
	{
		if (_count == null)
		{
			return 0;
		}
		final long count = _count.get();
		return count > 0 ? count : 0;
	}
	
	public void setCount(long currentCount)
	{
		if (_count == null)
		{
			_count = new AtomicLong();
		}
		_count.set(currentCount);
	}
	
	public boolean decreaseCount(long value)
	{
		if (_count == null)
		{
			return false;
		}
		
		BuyListTaskManager.getInstance().add(this, _restockDelay);
		
		final boolean result = _count.addAndGet(-value) >= 0;
		save();
		return result;
	}
	
	public boolean hasLimitedStock()
	{
		return _maxCount > -1;
	}
	
	public void restartRestockTask(long nextRestockTime)
	{
		final long remainTime = nextRestockTime - Chronos.currentTimeMillis();
		if (remainTime > 0)
		{
			BuyListTaskManager.getInstance().update(this, remainTime);
		}
		else
		{
			restock();
		}
	}
	
	public void restock()
	{
		setCount(_maxCount);
		save();
	}
	
	private void save()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement statement = con.prepareStatement("INSERT INTO `buylists`(`buylist_id`, `item_id`, `count`, `next_restock_time`) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE `count` = ?, `next_restock_time` = ?"))
		{
			statement.setInt(1, _buyListId);
			statement.setInt(2, _item.getId());
			statement.setLong(3, getCount());
			statement.setLong(5, getCount());
			
			final long nextRestockTime = BuyListTaskManager.getInstance().getRestockDelay(this);
			if (nextRestockTime > 0)
			{
				statement.setLong(4, nextRestockTime);
				statement.setLong(6, nextRestockTime);
			}
			else
			{
				statement.setLong(4, 0);
				statement.setLong(6, 0);
			}
			
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "Failed to save Product buylist_id:" + _buyListId + " item_id:" + _item.getId(), e);
		}
	}
}
