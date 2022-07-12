package org.l2jmobius.gameserver.model.beautyshop;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.StatSet;

/**
 * @author Sdw
 */
public class BeautyItem
{
	private final int _id;
	private final int _adena;
	private final int _resetAdena;
	private final int _beautyShopTicket;
	private final Map<Integer, BeautyItem> _colors = new HashMap<>();
	
	public BeautyItem(StatSet set)
	{
		_id = set.getInt("id");
		_adena = set.getInt("adena", 0);
		_resetAdena = set.getInt("reset_adena", 0);
		_beautyShopTicket = set.getInt("beauty_shop_ticket", 0);
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getAdena()
	{
		return _adena;
	}
	
	public int getResetAdena()
	{
		return _resetAdena;
	}
	
	public int getBeautyShopTicket()
	{
		return _beautyShopTicket;
	}
	
	public void addColor(StatSet set)
	{
		final BeautyItem color = new BeautyItem(set);
		_colors.put(set.getInt("id"), color);
	}
	
	public Map<Integer, BeautyItem> getColors()
	{
		return _colors;
	}
}
