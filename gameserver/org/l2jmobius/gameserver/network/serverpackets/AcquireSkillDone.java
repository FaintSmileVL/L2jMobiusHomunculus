package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Kerberos
 */
public class AcquireSkillDone implements IClientOutgoingPacket
{
	public AcquireSkillDone()
	{
		//
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ACQUIRE_SKILL_DONE.writeId(packet);
		return true;
	}
}