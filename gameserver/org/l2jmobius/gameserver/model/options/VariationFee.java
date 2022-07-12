package org.l2jmobius.gameserver.model.options;

/**
 * @author Pere
 */
public class VariationFee
{
	private final int _itemId;
	private final long _itemCount;
	private final long _cancelFee;
	
	public VariationFee(int itemId, long itemCount, long cancelFee)
	{
		_itemId = itemId;
		_itemCount = itemCount;
		_cancelFee = cancelFee;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public long getItemCount()
	{
		return _itemCount;
	}
	
	public long getCancelFee()
	{
		return _cancelFee;
	}
}