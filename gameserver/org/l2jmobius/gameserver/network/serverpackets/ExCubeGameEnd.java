package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * *
 * @author mrTJO
 */
public class ExCubeGameEnd implements IClientOutgoingPacket
{
	boolean _isRedTeamWin;
	
	/**
	 * Show Minigame Results
	 * @param isRedTeamWin Is Red Team Winner?
	 */
	public ExCubeGameEnd(boolean isRedTeamWin)
	{
		_isRedTeamWin = isRedTeamWin;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_STATE.writeId(packet);
		
		packet.writeD(0x01);
		
		packet.writeD(_isRedTeamWin ? 0x01 : 0x00);
		packet.writeD(0x00); // TODO: Find me!
		return true;
	}
}
