package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShowMiniMap implements IClientOutgoingPacket
{
	private final int _mapId;
	
	public ShowMiniMap(int mapId)
	{
		_mapId = mapId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHOW_MINIMAP.writeId(packet);
		
		packet.writeD(_mapId);
		packet.writeC(0x00); // Seven Signs state
		return true;
	}
}
