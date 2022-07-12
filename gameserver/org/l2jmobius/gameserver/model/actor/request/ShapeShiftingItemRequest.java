package org.l2jmobius.gameserver.model.actor.request;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author Sdw
 */
public class ShapeShiftingItemRequest extends AbstractRequest
{
	private ItemInstance _appearanceStone;
	private ItemInstance _appearanceExtractItem;
	
	public ShapeShiftingItemRequest(PlayerInstance player, ItemInstance appearanceStone)
	{
		super(player);
		_appearanceStone = appearanceStone;
	}
	
	public ItemInstance getAppearanceStone()
	{
		return _appearanceStone;
	}
	
	public void setAppearanceStone(ItemInstance appearanceStone)
	{
		_appearanceStone = appearanceStone;
	}
	
	public ItemInstance getAppearanceExtractItem()
	{
		return _appearanceExtractItem;
	}
	
	public void setAppearanceExtractItem(ItemInstance appearanceExtractItem)
	{
		_appearanceExtractItem = appearanceExtractItem;
	}
	
	@Override
	public boolean isItemRequest()
	{
		return true;
	}
	
	@Override
	public boolean canWorkWith(AbstractRequest request)
	{
		return !request.isItemRequest();
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		if ((_appearanceStone == null) || (_appearanceExtractItem == null))
		{
			return false;
		}
		return (objectId > 0) && ((objectId == _appearanceStone.getObjectId()) || (objectId == _appearanceExtractItem.getObjectId()));
	}
}
