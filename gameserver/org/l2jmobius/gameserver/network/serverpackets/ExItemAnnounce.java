package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author NviX
 */
public class ExItemAnnounce implements IClientOutgoingPacket
{
	private final ItemInstance _item;
	private final PlayerInstance _player;
	
	public ExItemAnnounce(ItemInstance item, PlayerInstance player)
	{
		_item = item;
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ITEM_ANNOUNCE.writeId(packet);
		packet.writeC(0x00); // item icon
		packet.writeString(_player.getName()); // name of player
		packet.writeD(_item.getId()); // item id
		packet.writeD(_item.getEnchantLevel()); // enchant level
		packet.writeC(0x00); // name of item
		return true;
	}
}