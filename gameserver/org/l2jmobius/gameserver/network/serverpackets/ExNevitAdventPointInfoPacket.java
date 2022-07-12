package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mochitto
 */
public class ExNevitAdventPointInfoPacket implements IClientOutgoingPacket
{
	private final int _points;
	
	public ExNevitAdventPointInfoPacket(int points)
	{
		_points = points;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BR_AGATHION_ENERGY_INFO.writeId(packet);
		
		packet.writeD(_points); // 72 = 1%, max 7200 = 100%
		return true;
	}
}
