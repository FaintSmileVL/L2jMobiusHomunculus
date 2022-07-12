package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SocialAction implements IClientOutgoingPacket
{
	// TODO: Enum
	public static final int LEVEL_UP = 2122;
	
	private final int _objectId;
	private final int _actionId;
	
	public SocialAction(int objectId, int actionId)
	{
		_objectId = objectId;
		_actionId = actionId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SOCIAL_ACTION.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_actionId);
		packet.writeD(0x00); // TODO: Find me!
		return true;
	}
}
