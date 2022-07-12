package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.TradeItem;
import org.l2jmobius.gameserver.model.items.Item;

/**
 * @author Mobius
 */
public class ClanShopProductHolder
{
	private final int _clanLevel;
	private final TradeItem _item;
	private final int _count;
	private final long _adena;
	private final int _fame;
	
	public ClanShopProductHolder(int clanLevel, Item item, int count, long adena, int fame)
	{
		_clanLevel = clanLevel;
		_item = new TradeItem(item, 0, 0);
		_count = count;
		_adena = adena;
		_fame = fame;
	}
	
	public int getClanLevel()
	{
		return _clanLevel;
	}
	
	public TradeItem getTradeItem()
	{
		return _item;
	}
	
	public int getCount()
	{
		return _count;
	}
	
	public long getAdena()
	{
		return _adena;
	}
	
	public int getFame()
	{
		return _fame;
	}
}
