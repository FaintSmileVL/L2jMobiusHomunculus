package org.l2jmobius.gameserver.network.clientpackets.friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.network.serverpackets.friend.FriendAddRequestResult;

public class RequestAnswerFriendInvite implements IClientIncomingPacket
{
	private int _response;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readC();
		_response = packet.readD();
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
		
		final PlayerInstance requestor = player.getActiveRequester();
		if (requestor == null)
		{
			return;
		}
		
		if (player == requestor)
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_ADD_YOURSELF_TO_YOUR_OWN_FRIEND_LIST);
			return;
		}
		
		if (player.getFriendList().contains(requestor.getObjectId()) //
			|| requestor.getFriendList().contains(player.getObjectId()))
		{
			final SystemMessage sm = new SystemMessage(SystemMessageId.C1_IS_ALREADY_ON_YOUR_FRIEND_LIST);
			sm.addString(player.getName());
			requestor.sendPacket(sm);
			return;
		}
		
		if (_response == 1)
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement("INSERT INTO character_friends (charId, friendId) VALUES (?, ?), (?, ?)"))
			{
				statement.setInt(1, requestor.getObjectId());
				statement.setInt(2, player.getObjectId());
				statement.setInt(3, player.getObjectId());
				statement.setInt(4, requestor.getObjectId());
				statement.execute();
				SystemMessage msg = new SystemMessage(SystemMessageId.THAT_PERSON_HAS_BEEN_SUCCESSFULLY_ADDED_TO_YOUR_FRIEND_LIST);
				requestor.sendPacket(msg);
				
				// Player added to your friend list
				msg = new SystemMessage(SystemMessageId.S1_HAS_BEEN_ADDED_TO_YOUR_FRIENDS_LIST);
				msg.addString(player.getName());
				requestor.sendPacket(msg);
				requestor.getFriendList().add(player.getObjectId());
				
				// has joined as friend.
				msg = new SystemMessage(SystemMessageId.S1_HAS_BEEN_ADDED_TO_YOUR_FRIENDS_LIST_2);
				msg.addString(requestor.getName());
				player.sendPacket(msg);
				player.getFriendList().add(requestor.getObjectId());
				
				// Send notifications for both player in order to show them online
				player.sendPacket(new FriendAddRequestResult(requestor, 1));
				requestor.sendPacket(new FriendAddRequestResult(player, 1));
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, "Could not add friend objectid: " + e.getMessage(), e);
			}
		}
		else
		{
			requestor.sendPacket(new SystemMessage(SystemMessageId.YOU_HAVE_FAILED_TO_ADD_A_FRIEND_TO_YOUR_FRIENDS_LIST));
		}
		
		player.setActiveRequester(null);
		requestor.onTransactionResponse();
	}
}
