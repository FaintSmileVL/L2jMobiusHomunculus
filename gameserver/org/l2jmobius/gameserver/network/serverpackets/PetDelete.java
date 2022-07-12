package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PetDelete implements IClientOutgoingPacket
{
	private final int _petType;
	private final int _petObjId;
	
	public PetDelete(int petType, int petObjId)
	{
		_petType = petType; // Summon Type
		_petObjId = petObjId; // objectId
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PET_DELETE.writeId(packet);
		
		packet.writeD(_petType);
		packet.writeD(_petObjId);
		return true;
	}
}
