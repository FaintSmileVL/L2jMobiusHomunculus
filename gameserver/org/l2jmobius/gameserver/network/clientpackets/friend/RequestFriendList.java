package org.l2jmobius.gameserver.network.clientpackets.friend;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.CharNameTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * @version $Revision: 1.3.4.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestFriendList implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
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
		
		SystemMessage sm;
		
		// ======<Friend List>======
		player.sendPacket(SystemMessageId.FRIENDS_LIST);
		
		PlayerInstance friend = null;
		for (int id : player.getFriendList())
		{
			// int friendId = rset.getInt("friendId");
			final String friendName = CharNameTable.getInstance().getNameById(id);
			if (friendName == null)
			{
				continue;
			}
			
			friend = World.getInstance().getPlayer(friendName);
			if ((friend == null) || !friend.isOnline())
			{
				// (Currently: Offline)
				sm = new SystemMessage(SystemMessageId.S1_CURRENTLY_OFFLINE);
				sm.addString(friendName);
			}
			else
			{
				// (Currently: Online)
				sm = new SystemMessage(SystemMessageId.S1_CURRENTLY_ONLINE);
				sm.addString(friendName);
			}
			
			player.sendPacket(sm);
		}
		
		// =========================
		player.sendPacket(SystemMessageId.EMPTY_3);
	}
}
