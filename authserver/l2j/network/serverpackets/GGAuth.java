package l2j.network.serverpackets;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.network.OutgoingPackets;

/**
 * Fromat: d d: response
 */
public class GGAuth implements IOutgoingPacket
{
	private final int _response;
	
	public GGAuth(int response)
	{
		_response = response;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GG_AUTH.writeId(packet);
		packet.writeD(_response);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		return true;
	}
}
