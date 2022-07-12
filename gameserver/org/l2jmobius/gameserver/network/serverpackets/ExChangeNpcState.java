package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExChangeNpcState implements IClientOutgoingPacket
{
	private final int _objId;
	private final int _state;
	
	public ExChangeNpcState(int objId, int state)
	{
		_objId = objId;
		_state = state;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_NPC_STATE.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeD(_state);
		return true;
	}
}
