package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.PartyDistributionType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExAskModifyPartyLooting implements IClientOutgoingPacket
{
	private final String _requestor;
	private final PartyDistributionType _partyDistributionType;
	
	public ExAskModifyPartyLooting(String name, PartyDistributionType partyDistributionType)
	{
		_requestor = name;
		_partyDistributionType = partyDistributionType;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ASK_MODIFY_PARTY_LOOTING.writeId(packet);
		
		packet.writeS(_requestor);
		packet.writeD(_partyDistributionType.getId());
		return true;
	}
}
