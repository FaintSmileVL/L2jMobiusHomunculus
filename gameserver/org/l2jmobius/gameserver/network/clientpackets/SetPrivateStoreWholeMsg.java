package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExPrivateStoreSetWholeMsg;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author KenM
 */
public class SetPrivateStoreWholeMsg implements IClientIncomingPacket
{
	private static final int MAX_MSG_LENGTH = 29;
	
	private String _msg;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_msg = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getSellList() == null))
		{
			return;
		}
		
		if ((_msg != null) && (_msg.length() > MAX_MSG_LENGTH))
		{
			Util.handleIllegalPlayerAction(player, "Player " + player.getName() + " tried to overflow private store whole message", Config.DEFAULT_PUNISH);
			return;
		}
		
		player.getSellList().setTitle(_msg);
		client.sendPacket(new ExPrivateStoreSetWholeMsg(player));
	}
}
