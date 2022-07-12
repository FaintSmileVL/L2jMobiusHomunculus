package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.HennaEquipList;

/**
 * @author Tempy, Zoey76
 */
public class RequestHennaItemList implements IClientIncomingPacket
{
	@SuppressWarnings("unused")
	private int _unknown;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_unknown = packet.readD(); // TODO: Identify.
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player != null)
		{
			player.sendPacket(new HennaEquipList(player));
		}
	}
}
