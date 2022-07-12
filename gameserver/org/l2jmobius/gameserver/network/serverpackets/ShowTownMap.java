package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShowTownMap implements IClientOutgoingPacket
{
	private final String _texture;
	private final int _x;
	private final int _y;
	
	public ShowTownMap(String texture, int x, int y)
	{
		_texture = texture;
		_x = x;
		_y = y;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOW_TOWN_MAP.writeId(packet);
		packet.writeS(_texture);
		packet.writeD(_x);
		packet.writeD(_y);
		return true;
	}
}
