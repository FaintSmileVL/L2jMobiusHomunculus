package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExNeedToChangeName;

/**
 * Reply for {@link ExNeedToChangeName}
 * @author JIV
 */
public class RequestExChangeName implements IClientIncomingPacket
{
	private String _newName;
	private int _type;
	private int _charSlot;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_type = packet.readD();
		_newName = packet.readS();
		_charSlot = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		LOGGER.info("Recieved " + getClass().getSimpleName() + " name: " + _newName + " type: " + _type + " CharSlot: " + _charSlot);
	}
}
