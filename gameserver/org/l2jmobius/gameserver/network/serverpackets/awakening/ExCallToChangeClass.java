package org.l2jmobius.gameserver.network.serverpackets.awakening;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExCallToChangeClass implements IClientOutgoingPacket
{
	private final int _classId;
	private final boolean _showMessage;
	
	public ExCallToChangeClass(int classId, boolean showMessage)
	{
		_classId = classId;
		_showMessage = showMessage;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CALL_TO_CHANGE_CLASS.writeId(packet);
		packet.writeD(_classId);
		packet.writeD(_showMessage ? 1 : 0);
		packet.writeD(1); // Force - 0 you have to do it; 1 it's optional
		return true;
	}
}
