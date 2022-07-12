package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Close Minigame Waiting List
 * @author mrTJO
 */
public class ExCubeGameCloseUI implements IClientOutgoingPacket
{
	public static final ExCubeGameCloseUI STATIC_PACKET = new ExCubeGameCloseUI();
	
	private ExCubeGameCloseUI()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BLOCK_UP_SET_LIST.writeId(packet);
		
		packet.writeD(0xffffffff);
		return true;
	}
}
