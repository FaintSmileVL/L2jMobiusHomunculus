package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class PackageSendableList extends AbstractItemPacket
{
	private final Collection<ItemInstance> _items;
	private final int _objectId;
	private final long _adena;
	private final int _sendType;
	
	public PackageSendableList(int sendType, PlayerInstance player, int objectId)
	{
		_sendType = sendType;
		_items = player.getInventory().getAvailableItems(true, true, true);
		_objectId = objectId;
		_adena = player.getAdena();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PACKAGE_SENDABLE_LIST.writeId(packet);
		
		packet.writeC(_sendType);
		if (_sendType == 2)
		{
			packet.writeD(_items.size());
			packet.writeD(_items.size());
			for (ItemInstance item : _items)
			{
				writeItem(packet, item);
				packet.writeD(item.getObjectId());
			}
		}
		else
		{
			packet.writeD(_objectId);
			packet.writeQ(_adena);
			packet.writeD(_items.size());
		}
		return true;
	}
}
