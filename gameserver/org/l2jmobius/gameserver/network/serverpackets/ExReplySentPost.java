package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Message;
import org.l2jmobius.gameserver.model.itemcontainer.ItemContainer;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Migi, DS
 */
public class ExReplySentPost extends AbstractItemPacket
{
	private final Message _msg;
	private Collection<ItemInstance> _items = null;
	
	public ExReplySentPost(Message msg)
	{
		_msg = msg;
		if (msg.hasAttachments())
		{
			final ItemContainer attachments = msg.getAttachments();
			if ((attachments != null) && (attachments.getSize() > 0))
			{
				_items = attachments.getItems();
			}
			else
			{
				LOGGER.warning("Message " + msg.getId() + " has attachments but itemcontainer is empty.");
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_REPLY_SENT_POST.writeId(packet);
		
		packet.writeD(0x00); // GOD
		packet.writeD(_msg.getId());
		packet.writeD(_msg.isLocked() ? 1 : 0);
		packet.writeS(_msg.getReceiverName());
		packet.writeS(_msg.getSubject());
		packet.writeS(_msg.getContent());
		
		if ((_items != null) && !_items.isEmpty())
		{
			packet.writeD(_items.size());
			for (ItemInstance item : _items)
			{
				writeItem(packet, item);
				packet.writeD(item.getObjectId());
			}
		}
		else
		{
			packet.writeD(0x00);
		}
		packet.writeQ(_msg.getReqAdena());
		packet.writeD(_msg.hasAttachments() ? 0x01 : 0x00);
		packet.writeD(_msg.isReturned() ? 0x01 : 00);
		return true;
	}
}
