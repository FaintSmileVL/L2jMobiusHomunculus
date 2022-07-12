package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class DropItem implements IClientOutgoingPacket
{
	private final ItemInstance _item;
	private final int _objectId;
	
	/**
	 * Constructor of the DropItem server packet
	 * @param item : ItemInstance designating the item
	 * @param playerObjId : int designating the player ID who dropped the item
	 */
	public DropItem(ItemInstance item, int playerObjId)
	{
		_item = item;
		_objectId = playerObjId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.DROP_ITEM.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_item.getObjectId());
		packet.writeD(_item.getDisplayId());
		
		packet.writeD(_item.getX());
		packet.writeD(_item.getY());
		packet.writeD(_item.getZ());
		// only show item count if it is a stackable item
		packet.writeC(_item.isStackable() ? 0x01 : 0x00);
		packet.writeQ(_item.getCount());
		
		packet.writeC(0x00);
		// packet.writeD(0x01); if above C == true (1) then packet.readD()
		packet.writeC(_item.getEnchantLevel()); // Grand Crusade
		packet.writeC(_item.getAugmentation() != null ? 1 : 0); // Grand Crusade
		packet.writeC(_item.getSpecialAbilities().size()); // Grand Crusade
		return true;
	}
}
