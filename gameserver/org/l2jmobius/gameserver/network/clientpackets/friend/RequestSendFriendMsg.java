package org.l2jmobius.gameserver.network.clientpackets.friend;

import java.util.logging.Logger;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.L2FriendSay;

/**
 * Recieve Private (Friend) Message - 0xCC Format: c SS S: Message S: Receiving Player
 * @author Tempy
 */
public class RequestSendFriendMsg implements IClientIncomingPacket
{
	private static Logger LOGGER_CHAT = Logger.getLogger("chat");
	
	private String _message;
	private String _reciever;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_message = packet.readS();
		_reciever = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if ((_message == null) || _message.isEmpty() || (_message.length() > 300))
		{
			return;
		}
		
		final PlayerInstance targetPlayer = World.getInstance().getPlayer(_reciever);
		if ((targetPlayer == null) || !targetPlayer.getFriendList().contains(player.getObjectId()))
		{
			player.sendPacket(SystemMessageId.THAT_PLAYER_IS_NOT_ONLINE);
			return;
		}
		
		if (Config.LOG_CHAT)
		{
			LOGGER_CHAT.info("PRIV_MSG [" + player + " to " + targetPlayer + "] " + _message);
		}
		
		targetPlayer.sendPacket(new L2FriendSay(player.getName(), _reciever, _message));
	}
}
