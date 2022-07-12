package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExChangeToAwakenedClass implements IClientOutgoingPacket
{
	private final int _classId;
	
	public ExChangeToAwakenedClass(int classId)
	{
		_classId = classId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_TO_AWAKENED_CLASS.writeId(packet);
		
		packet.writeD(_classId);
		return true;
	}
}