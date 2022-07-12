package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.enums.MailType;
import org.l2jmobius.gameserver.instancemanager.MailManager;
import org.l2jmobius.gameserver.model.Message;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * @author Migi, DS
 */
public class ExShowReceivedPostList implements IClientOutgoingPacket
{
	private final List<Message> _inbox;
	
	private static final int MESSAGE_FEE = 100;
	private static final int MESSAGE_FEE_PER_SLOT = 1000;
	
	public ExShowReceivedPostList(int objectId)
	{
		_inbox = MailManager.getInstance().getInbox(objectId);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_RECEIVED_POST_LIST.writeId(packet);
		
		packet.writeD((int) (Chronos.currentTimeMillis() / 1000));
		if ((_inbox != null) && !_inbox.isEmpty())
		{
			packet.writeD(_inbox.size());
			for (Message msg : _inbox)
			{
				packet.writeD(msg.getMailType().ordinal());
				if (msg.getMailType() == MailType.COMMISSION_ITEM_SOLD)
				{
					packet.writeD(SystemMessageId.THE_ITEM_YOU_REGISTERED_HAS_BEEN_SOLD.getId());
				}
				else if (msg.getMailType() == MailType.COMMISSION_ITEM_RETURNED)
				{
					packet.writeD(SystemMessageId.THE_REGISTRATION_PERIOD_FOR_THE_ITEM_YOU_REGISTERED_HAS_EXPIRED.getId());
				}
				packet.writeD(msg.getId());
				packet.writeS(msg.getSubject());
				packet.writeS(msg.getSenderName());
				packet.writeD(msg.isLocked() ? 0x01 : 0x00);
				packet.writeD(msg.getExpirationSeconds());
				packet.writeD(msg.isUnread() ? 0x01 : 0x00);
				packet.writeD(((msg.getMailType() == MailType.COMMISSION_ITEM_SOLD) || (msg.getMailType() == MailType.COMMISSION_ITEM_RETURNED)) ? 0 : 1);
				packet.writeD(msg.hasAttachments() ? 0x01 : 0x00);
				packet.writeD(msg.isReturned() ? 0x01 : 0x00);
				packet.writeD(0x00); // SysString in some case it seems
			}
		}
		else
		{
			packet.writeD(0x00);
		}
		packet.writeD(MESSAGE_FEE);
		packet.writeD(MESSAGE_FEE_PER_SLOT);
		return true;
	}
}
