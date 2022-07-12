package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author godson
 */
public class ExOlympiadMode implements IClientOutgoingPacket
{
	private final int _mode;
	
	/**
	 * @param mode (0 = return, 3 = spectate)
	 */
	public ExOlympiadMode(int mode)
	{
		_mode = mode;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_OLYMPIAD_MODE.writeId(packet);
		
		packet.writeC(_mode);
		return true;
	}
}
