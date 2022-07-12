package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExShowBaseAttributeCancelWindow implements IClientOutgoingPacket
{
	private final Collection<ItemInstance> _items;
	private long _price;
	
	public ExShowBaseAttributeCancelWindow(PlayerInstance player)
	{
		_items = player.getInventory().getItems(ItemInstance::hasAttributes);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_BASE_ATTRIBUTE_CANCEL_WINDOW.writeId(packet);
		
		packet.writeD(_items.size());
		for (ItemInstance item : _items)
		{
			packet.writeD(item.getObjectId());
			packet.writeQ(getPrice(item));
		}
		return true;
	}
	
	/**
	 * TODO: Unhardcode! Update prices for Top/Mid/Low S80/S84
	 * @param item
	 * @return
	 */
	private long getPrice(ItemInstance item)
	{
		switch (item.getItem().getCrystalType())
		{
			case S:
			{
				if (item.isWeapon())
				{
					_price = 50000;
				}
				else
				{
					_price = 40000;
				}
				break;
			}
			case S80:
			{
				if (item.isWeapon())
				{
					_price = 100000;
				}
				else
				{
					_price = 80000;
				}
				break;
			}
			case S84:
			{
				if (item.isWeapon())
				{
					_price = 200000;
				}
				else
				{
					_price = 160000;
				}
				break;
			}
		}
		return _price;
	}
}
