package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mochitto
 */
public class ExNevitAdventTimeChange implements IClientOutgoingPacket
{
	private final boolean _paused;
	private final int _time;
	
	public ExNevitAdventTimeChange(int time)
	{
		_time = time > 240000 ? 240000 : time;
		_paused = _time < 1;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_GET_CRYSTALIZING_ESTIMATION.writeId(packet);
		
		// state 0 - pause 1 - started
		packet.writeC(_paused ? 0x00 : 0x01);
		// left time in ms max is 16000 its 4m and state is automatically changed to quit
		packet.writeD(_time);
		return true;
	}
}
