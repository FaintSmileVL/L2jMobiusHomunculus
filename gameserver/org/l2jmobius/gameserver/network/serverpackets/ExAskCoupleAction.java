package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExAskCoupleAction implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _actionId;
	
	public ExAskCoupleAction(int charObjId, int social)
	{
		_objectId = charObjId;
		_actionId = social;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ASK_COUPLE_ACTION.writeId(packet);
		
		packet.writeD(_actionId);
		packet.writeD(_objectId);
		return true;
	}
}
