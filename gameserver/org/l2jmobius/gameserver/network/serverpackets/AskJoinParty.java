package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.PartyDistributionType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class AskJoinParty implements IClientOutgoingPacket
{
	private final String _requestorName;
	private final PartyDistributionType _partyDistributionType;
	
	/**
	 * @param requestorName
	 * @param partyDistributionType
	 */
	public AskJoinParty(String requestorName, PartyDistributionType partyDistributionType)
	{
		_requestorName = requestorName;
		_partyDistributionType = partyDistributionType;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ASK_JOIN_PARTY.writeId(packet);
		
		packet.writeS(_requestorName);
		packet.writeD(_partyDistributionType.getId());
		return true;
	}
}
