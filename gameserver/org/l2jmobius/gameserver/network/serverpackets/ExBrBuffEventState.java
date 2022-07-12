package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Eva's Inferno event packet. info params:<br>
 * type (1 - %, 2 - npcId),<br>
 * value (depending on type: for type 1 - % value; for type 2 - 20573-20575),<br>
 * state (0-1), endtime (only when type 2)
 */
public class ExBrBuffEventState implements IClientOutgoingPacket
{
	private final int _type; // 1 - %, 2 - npcId
	private final int _value; // depending on type: for type 1 - % value; for type 2 - 20573-20575
	private final int _state; // 0-1
	private final int _endtime; // only when type 2 as unix time in seconds from 1970
	
	public ExBrBuffEventState(int type, int value, int state, int endtime)
	{
		_type = type;
		_value = value;
		_state = state;
		_endtime = endtime;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BR_BUFF_EVENT_STATE.writeId(packet);
		
		packet.writeD(_type);
		packet.writeD(_value);
		packet.writeD(_state);
		packet.writeD(_endtime);
		return true;
	}
}
