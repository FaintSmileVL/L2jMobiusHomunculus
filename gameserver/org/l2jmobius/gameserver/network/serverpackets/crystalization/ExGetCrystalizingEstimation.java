package org.l2jmobius.gameserver.network.serverpackets.crystalization;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.holders.ItemChanceHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExGetCrystalizingEstimation implements IClientOutgoingPacket
{
	private final List<ItemChanceHolder> _items;
	
	public ExGetCrystalizingEstimation(List<ItemChanceHolder> items)
	{
		_items = items;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_GET_CRYSTALIZING_ESTIMATION.writeId(packet);
		
		packet.writeD(_items.size());
		for (ItemChanceHolder holder : _items)
		{
			packet.writeD(holder.getId());
			packet.writeQ(holder.getCount());
			packet.writeF(holder.getChance());
		}
		return true;
	}
}
