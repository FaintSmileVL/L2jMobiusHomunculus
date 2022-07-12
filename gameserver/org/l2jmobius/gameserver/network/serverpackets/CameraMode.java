package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class CameraMode implements IClientOutgoingPacket
{
	private final int _mode;
	
	/**
	 * Forces client camera mode change
	 * @param mode 0 - third person cam 1 - first person cam
	 */
	public CameraMode(int mode)
	{
		_mode = mode;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CAMERA_MODE.writeId(packet);
		
		packet.writeD(_mode);
		return true;
	}
}
