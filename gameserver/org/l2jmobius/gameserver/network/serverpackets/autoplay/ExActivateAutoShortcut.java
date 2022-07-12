package org.l2jmobius.gameserver.network.serverpackets.autoplay;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author JoeAlisson
 */
public class ExActivateAutoShortcut implements IClientOutgoingPacket
{
	private final int _room;
	private final boolean _activate;
	
	public ExActivateAutoShortcut(int room, boolean activate)
	{
		_room = room;
		_activate = activate;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ACTIVATE_AUTO_SHORTCUT.writeId(packet);
		packet.writeH(_room);
		packet.writeC(_activate ? 0x01 : 0x00);
		return true;
	}
}
