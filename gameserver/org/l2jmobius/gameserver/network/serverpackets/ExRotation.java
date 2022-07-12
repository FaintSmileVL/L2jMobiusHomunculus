package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExRotation implements IClientOutgoingPacket
{
	private final int _charId;
	private final int _heading;
	
	public ExRotation(int charId, int heading)
	{
		_charId = charId;
		_heading = heading;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ROTATION.writeId(packet);
		
		packet.writeD(_charId);
		packet.writeD(_heading);
		return true;
	}
}
