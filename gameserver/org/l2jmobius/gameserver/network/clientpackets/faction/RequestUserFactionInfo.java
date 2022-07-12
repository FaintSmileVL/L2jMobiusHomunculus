package org.l2jmobius.gameserver.network.clientpackets.faction;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.faction.ExFactionInfo;

/**
 * @author Mathael
 */
public class RequestUserFactionInfo implements IClientIncomingPacket
{
	private boolean _openDialog;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readD();
		_openDialog = packet.readC() != 0;
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		client.getPlayer().sendPacket(new ExFactionInfo(client.getPlayer(), _openDialog));
	}
}