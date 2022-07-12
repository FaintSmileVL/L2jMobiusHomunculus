package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class DoorStatusUpdate implements IClientOutgoingPacket
{
	private final DoorInstance _door;
	
	public DoorStatusUpdate(DoorInstance door)
	{
		_door = door;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.DOOR_STATUS_UPDATE.writeId(packet);
		
		packet.writeD(_door.getObjectId());
		packet.writeD(_door.isOpen() ? 0 : 1);
		packet.writeD(_door.getDamage());
		packet.writeD(_door.isEnemy() ? 1 : 0);
		packet.writeD(_door.getId());
		packet.writeD((int) _door.getCurrentHp());
		packet.writeD(_door.getMaxHp());
		return true;
	}
}