package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.MailType;
import org.l2jmobius.gameserver.model.Message;
import org.l2jmobius.gameserver.model.itemcontainer.ItemContainer;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * @author Migi, DS
 */
public class ExReplyReceivedPost extends AbstractItemPacket
{
	private final Message _msg;
	private Collection<ItemInstance> _items = null;
	
	public ExReplyReceivedPost(Message msg)
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
		OutgoingPackets.EX_REPLY_RECEIVED_POST.writeId(packet);
		
		packet.writeD(_msg.getMailType().ordinal()); // GOD
		if (_msg.getMailType() == MailType.COMMISSION_ITEM_RETURNED)
		{
			packet.writeD(SystemMessageId.THE_REGISTRATION_PERIOD_FOR_THE_ITEM_YOU_REGISTERED_HAS_EXPIRED.getId());
			packet.writeD(SystemMessageId.THE_AUCTION_HOUSE_REGISTRATION_PERIOD_HAS_EXPIRED_AND_THE_CORRESPONDING_ITEM_IS_BEING_FORWARDED.getId());
		}
		else if (_msg.getMailType() == MailType.COMMISSION_ITEM_SOLD)
		{
			packet.writeD(_msg.getItemId());
			packet.writeD(_msg.getEnchantLvl());
			for (int i = 0; i < 6; i++)
			{
				packet.writeD(_msg.getElementals()[i]);
			}
			packet.writeD(SystemMessageId.THE_ITEM_YOU_REGISTERED_HAS_BEEN_SOLD.getId());
			packet.writeD(SystemMessageId.S1_HAS_BEEN_SOLD.getId());
		}
		packet.writeD(_msg.getId());
		packet.writeD(_msg.isLocked() ? 1 : 0);
		packet.writeD(0x00); // Unknown
		packet.writeS(_msg.getSenderName());
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
		packet.writeD(_msg.hasAttachments() ? 1 : 0);
		packet.writeD(_msg.isReturned() ? 1 : 0);
		return true;
	}
}
