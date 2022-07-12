package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class AutoAttackStart implements IClientOutgoingPacket
{
	private final int _targetObjId;
	
	/**
	 * @param targetId
	 */
	public AutoAttackStart(int targetId)
	{
		_targetObjId = targetId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.AUTO_ATTACK_START.writeId(packet);
		
		packet.writeD(_targetObjId);
		return true;
	}
}
