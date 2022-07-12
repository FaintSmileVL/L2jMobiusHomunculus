package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author ShanSoft
 */
public class ExBuySellList extends AbstractItemPacket
{
	private final Collection<ItemInstance> _sellList;
	private Collection<ItemInstance> _refundList = null;
	private final boolean _done;
	private final int _inventorySlots;
	
	public ExBuySellList(PlayerInstance player, boolean done)
	{
		final Summon pet = player.getPet();
		_sellList = player.getInventory().getItems(item -> !item.isEquipped() && item.isSellable() && ((pet == null) || (item.getObjectId() != pet.getControlObjectId())));
		_inventorySlots = player.getInventory().getItems(item -> !item.isQuestItem()).size();
		if (player.hasRefund())
		{
			_refundList = player.getRefund().getItems();
		}
		_done = done;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BUY_SELL_LIST.writeId(packet);
		
		packet.writeD(0x01); // Type SELL
		packet.writeD(_inventorySlots);
		
		if ((_sellList != null))
		{
			packet.writeH(_sellList.size());
			for (ItemInstance item : _sellList)
			{
				writeItem(packet, item);
				packet.writeQ(Config.MERCHANT_ZERO_SELL_PRICE ? 0 : item.getItem().getReferencePrice() / 2);
			}
		}
		else
		{
			packet.writeH(0x00);
		}
		
		if ((_refundList != null) && !_refundList.isEmpty())
		{
			packet.writeH(_refundList.size());
			int i = 0;
			for (ItemInstance item : _refundList)
			{
				writeItem(packet, item);
				packet.writeD(i++);
				packet.writeQ(Config.MERCHANT_ZERO_SELL_PRICE ? 0 : (item.getItem().getReferencePrice() / 2) * item.getCount());
			}
		}
		else
		{
			packet.writeH(0x00);
		}
		
		packet.writeC(_done ? 0x01 : 0x00);
		return true;
	}
}
