package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.PartyDistributionType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExSetPartyLooting implements IClientOutgoingPacket
{
	private final int _result;
	private final PartyDistributionType _partyDistributionType;
	
	public ExSetPartyLooting(int result, PartyDistributionType partyDistributionType)
	{
		_result = result;
		_partyDistributionType = partyDistributionType;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SET_PARTY_LOOTING.writeId(packet);
		
		packet.writeD(_result);
		packet.writeD(_partyDistributionType.getId());
		return true;
	}
}
