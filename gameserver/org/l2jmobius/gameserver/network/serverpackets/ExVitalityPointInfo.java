package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author GodKratos
 */
public class ExVitalityPointInfo implements IClientOutgoingPacket
{
	private final int _vitalityPoints;
	
	public ExVitalityPointInfo(int vitPoints)
	{
		_vitalityPoints = vitPoints;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_VITALITY_POINT_INFO.writeId(packet);
		
		packet.writeD(_vitalityPoints);
		return true;
	}
}
