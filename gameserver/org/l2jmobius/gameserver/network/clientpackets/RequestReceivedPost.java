package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.MailManager;
import org.l2jmobius.gameserver.model.Message;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExChangePostState;
import org.l2jmobius.gameserver.network.serverpackets.ExReplyReceivedPost;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author Migi, DS
 */
public class RequestReceivedPost implements IClientIncomingPacket
{
	private int _msgId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_msgId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || !Config.ALLOW_MAIL)
		{
			return;
		}
		
		final Message msg = MailManager.getInstance().getMessage(_msgId);
		if (msg == null)
		{
			return;
		}
		
		if (!player.isInsideZone(ZoneId.PEACE) && msg.hasAttachments())
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_RECEIVE_OR_SEND_MAIL_WITH_ATTACHED_ITEMS_IN_NON_PEACE_ZONE_REGIONS);
			return;
		}
		
		if (msg.getReceiverId() != player.getObjectId())
		{
			Util.handleIllegalPlayerAction(player, "Player " + player.getName() + " tried to receive not own post!", Config.DEFAULT_PUNISH);
			return;
		}
		
		if (msg.isDeletedByReceiver())
		{
			return;
		}
		
		client.sendPacket(new ExReplyReceivedPost(msg));
		client.sendPacket(new ExChangePostState(true, _msgId, Message.READED));
		msg.markAsRead();
	}
}
