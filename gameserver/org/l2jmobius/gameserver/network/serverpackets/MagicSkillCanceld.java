package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class MagicSkillCanceld implements IClientOutgoingPacket
{
	private final int _objectId;
	
	public MagicSkillCanceld(int objectId)
	{
		_objectId = objectId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MAGIC_SKILL_CANCELED.writeId(packet);
		
		packet.writeD(_objectId);
		return true;
	}
}
