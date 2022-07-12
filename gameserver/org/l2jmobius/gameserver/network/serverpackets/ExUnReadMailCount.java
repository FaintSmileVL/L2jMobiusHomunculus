package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.MailManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExUnReadMailCount implements IClientOutgoingPacket
{
	private final int _mailUnreadCount;
	
	public ExUnReadMailCount(PlayerInstance player)
	{
		_mailUnreadCount = (int) MailManager.getInstance().getUnreadCount(player);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_UN_READ_MAIL_COUNT.writeId(packet);
		
		packet.writeD(_mailUnreadCount);
		return true;
	}
}
