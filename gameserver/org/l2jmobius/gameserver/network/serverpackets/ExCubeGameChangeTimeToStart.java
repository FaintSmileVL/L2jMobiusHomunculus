package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class ExCubeGameChangeTimeToStart implements IClientOutgoingPacket
{
	int _seconds;
	
	/**
	 * Update Minigame Waiting List Time to Start
	 * @param seconds
	 */
	public ExCubeGameChangeTimeToStart(int seconds)
	{
		_seconds = seconds;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_LIST.writeId(packet);
		
		packet.writeD(0x03);
		
		packet.writeD(_seconds);
		return true;
	}
}
