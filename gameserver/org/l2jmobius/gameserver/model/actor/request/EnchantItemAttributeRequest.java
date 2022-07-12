package org.l2jmobius.gameserver.model.actor.request;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class EnchantItemAttributeRequest extends AbstractRequest
{
	private volatile int _enchantingItemObjectId;
	private volatile int _enchantingStoneObjectId;
	
	public EnchantItemAttributeRequest(PlayerInstance player, int enchantingStoneObjectId)
	{
		super(player);
		_enchantingStoneObjectId = enchantingStoneObjectId;
	}
	
	public ItemInstance getEnchantingItem()
	{
		return getActiveChar().getInventory().getItemByObjectId(_enchantingItemObjectId);
	}
	
	public void setEnchantingItem(int objectId)
	{
		_enchantingItemObjectId = objectId;
	}
	
	public ItemInstance getEnchantingStone()
	{
		return getActiveChar().getInventory().getItemByObjectId(_enchantingStoneObjectId);
	}
	
	public void setEnchantingStone(int objectId)
	{
		_enchantingStoneObjectId = objectId;
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
		return (objectId > 0) && ((objectId == _enchantingItemObjectId) || (objectId == _enchantingStoneObjectId));
	}
}
