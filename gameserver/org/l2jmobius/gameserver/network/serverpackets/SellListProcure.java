package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.CropProcure;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SellListProcure implements IClientOutgoingPacket
{
	private final long _money;
	private final Map<ItemInstance, Long> _sellList = new HashMap<>();
	
	public SellListProcure(PlayerInstance player, int castleId)
	{
		_money = player.getAdena();
		for (CropProcure c : CastleManorManager.getInstance().getCropProcure(castleId, false))
		{
			final ItemInstance item = player.getInventory().getItemByItemId(c.getId());
			if ((item != null) && (c.getAmount() > 0))
			{
				_sellList.put(item, c.getAmount());
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SELL_LIST_PROCURE.writeId(packet);
		
		packet.writeQ(_money); // money
		packet.writeD(0x00); // lease ?
		packet.writeH(_sellList.size()); // list size
		
		for (Entry<ItemInstance, Long> entry : _sellList.entrySet())
		{
			final ItemInstance item = entry.getKey();
			packet.writeH(item.getItem().getType1());
			packet.writeD(item.getObjectId());
			packet.writeD(item.getDisplayId());
			packet.writeQ(entry.getValue()); // count
			packet.writeH(item.getItem().getType2());
			packet.writeH(0); // unknown
			packet.writeQ(0); // price, u shouldnt get any adena for crops, only raw materials
		}
		return true;
	}
}
