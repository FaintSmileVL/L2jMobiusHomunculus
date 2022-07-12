package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class AutoAttackStop implements IClientOutgoingPacket
{
	private final int _targetObjId;
	
	/**
	 * @param targetObjId
	 */
	public AutoAttackStop(int targetObjId)
	{
		_targetObjId = targetObjId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.AUTO_ATTACK_STOP.writeId(packet);
		
		packet.writeD(_targetObjId);
		return true;
	}
}
