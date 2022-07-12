package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.enums.MailType;
import org.l2jmobius.gameserver.instancemanager.MailManager;
import org.l2jmobius.gameserver.model.Message;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExChangePostState;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author Migi, DS
 */
public class RequestRejectPostAttachment implements IClientIncomingPacket
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
		if (!Config.ALLOW_MAIL || !Config.ALLOW_ATTACHMENTS)
		{
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("rejectattach"))
		{
			return;
		}
		
		if (!player.isInsideZone(ZoneId.PEACE))
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_RECEIVE_OR_SEND_MAIL_WITH_ATTACHED_ITEMS_IN_NON_PEACE_ZONE_REGIONS);
			return;
		}
		
		final Message msg = MailManager.getInstance().getMessage(_msgId);
		if (msg == null)
		{
			return;
		}
		
		if (msg.getReceiverId() != player.getObjectId())
		{
			Util.handleIllegalPlayerAction(player, "Player " + player.getName() + " tried to reject not own attachment!", Config.DEFAULT_PUNISH);
			return;
		}
		
		if (!msg.hasAttachments() || (msg.getMailType() != MailType.REGULAR))
		{
			return;
		}
		
		MailManager.getInstance().sendMessage(new Message(msg));
		client.sendPacket(SystemMessageId.MAIL_SUCCESSFULLY_RETURNED);
		client.sendPacket(new ExChangePostState(true, _msgId, Message.REJECTED));
		
		final PlayerInstance sender = World.getInstance().getPlayer(msg.getSenderId());
		if (sender != null)
		{
			final SystemMessage sm = new SystemMessage(SystemMessageId.S1_RETURNED_THE_MAIL);
			sm.addString(player.getName());
			sender.sendPacket(sm);
		}
	}
}
