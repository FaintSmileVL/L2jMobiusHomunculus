package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class DoorInfo implements IClientOutgoingPacket
{
	private final DoorInstance _door;
	
	public DoorInfo(DoorInstance door)
	{
		_door = door;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.DOOR_INFO.writeId(packet);
		
		packet.writeD(_door.getObjectId());
		packet.writeD(_door.getId());
		return true;
	}
}
