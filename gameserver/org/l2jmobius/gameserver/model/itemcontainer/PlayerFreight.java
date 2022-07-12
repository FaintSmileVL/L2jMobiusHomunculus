package org.l2jmobius.gameserver.model.itemcontainer;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public class PlayerFreight extends ItemContainer
{
	private final PlayerInstance _owner;
	private final int _ownerId;
	
	public PlayerFreight(int objectId)
	{
		_owner = null;
		_ownerId = objectId;
		restore();
	}
	
	public PlayerFreight(PlayerInstance owner)
	{
		_owner = owner;
		_ownerId = owner.getObjectId();
	}
	
	@Override
	public int getOwnerId()
	{
		return _ownerId;
	}
	
	@Override
	public PlayerInstance getOwner()
	{
		return _owner;
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.FREIGHT;
	}
	
	@Override
	public String getName()
	{
		return "Freight";
	}
	
	@Override
	public boolean validateCapacity(long slots)
	{
		return ((getSize() + slots) <= Config.ALT_FREIGHT_SLOTS);
	}
	
	@Override
	public void refreshWeight()
	{
	}
}