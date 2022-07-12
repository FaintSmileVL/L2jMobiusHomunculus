package org.l2jmobius.gameserver.network.clientpackets.friend;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.friend.ExFriendDetailInfo;

/**
 * @author Sdw
 */
public class RequestFriendDetailInfo implements IClientIncomingPacket
{
	private String _name;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_name = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player != null)
		{
			client.sendPacket(new ExFriendDetailInfo(player, _name));
		}
	}
}
