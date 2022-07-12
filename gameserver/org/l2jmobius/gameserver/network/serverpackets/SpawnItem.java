package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SpawnItem implements IClientOutgoingPacket
{
	private final ItemInstance _item;
	
	public SpawnItem(ItemInstance item)
	{
		_item = item;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SPAWN_ITEM.writeId(packet);
		
		packet.writeD(_item.getObjectId());
		packet.writeD(_item.getDisplayId());
		packet.writeD(_item.getX());
		packet.writeD(_item.getY());
		packet.writeD(_item.getZ());
		// only show item count if it is a stackable item
		packet.writeD(_item.isStackable() ? 0x01 : 0x00);
		packet.writeQ(_item.getCount());
		packet.writeD(0x00); // c2
		packet.writeC(_item.getEnchantLevel()); // Grand Crusade
		packet.writeC(_item.getAugmentation() != null ? 1 : 0); // Grand Crusade
		packet.writeC(_item.getSpecialAbilities().size()); // Grand Crusade
		return true;
	}
}
