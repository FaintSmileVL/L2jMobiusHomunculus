package org.l2jmobius.gameserver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.l2jmobius.Config;
import util.Chronos;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.instancemanager.ItemsOnGroundManager;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

public class ItemsAutoDestroy
{
	private final List<ItemInstance> _items = new LinkedList<>();
	
	protected ItemsAutoDestroy()
	{
		ThreadPool.scheduleAtFixedRate(this::removeItems, 5000, 5000);
	}
	
	public static ItemsAutoDestroy getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	public synchronized void addItem(ItemInstance item)
	{
		item.setDropTime(Chronos.currentTimeMillis());
		_items.add(item);
	}
	
	private synchronized void removeItems()
	{
		if (_items.isEmpty())
		{
			return;
		}
		
		final long curtime = Chronos.currentTimeMillis();
		final Iterator<ItemInstance> itemIterator = _items.iterator();
		while (itemIterator.hasNext())
		{
			final ItemInstance item = itemIterator.next();
			if ((item.getDropTime() == 0) || (item.getItemLocation() != ItemLocation.VOID))
			{
				itemIterator.remove();
			}
			else
			{
				final long autoDestroyTime;
				if (item.getItem().getAutoDestroyTime() > 0)
				{
					autoDestroyTime = item.getItem().getAutoDestroyTime();
				}
				else if (item.getItem().hasExImmediateEffect())
				{
					autoDestroyTime = Config.HERB_AUTO_DESTROY_TIME;
				}
				else
				{
					autoDestroyTime = ((Config.AUTODESTROY_ITEM_AFTER == 0) ? 3600000 : Config.AUTODESTROY_ITEM_AFTER * 1000);
				}
				
				if ((curtime - item.getDropTime()) > autoDestroyTime)
				{
					item.decayMe();
					itemIterator.remove();
					if (Config.SAVE_DROPPED_ITEM)
					{
						ItemsOnGroundManager.getInstance().removeObject(item);
					}
				}
			}
		}
	}
	
	private static class SingletonHolder
	{
		protected static final ItemsAutoDestroy INSTANCE = new ItemsAutoDestroy();
	}
}