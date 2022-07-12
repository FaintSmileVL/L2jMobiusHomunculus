package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExMPCCPartymasterList implements IClientOutgoingPacket
{
	private final Set<String> _leadersName;
	
	public ExMPCCPartymasterList(Set<String> leadersName)
	{
		_leadersName = leadersName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MPCC_PARTYMASTER_LIST.writeId(packet);
		
		packet.writeD(_leadersName.size());
		_leadersName.forEach(packet::writeS);
		return true;
	}
}
