package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author chris_00
 */
public class ExMPCCPartyInfoUpdate implements IClientOutgoingPacket
{
	private final int _mode;
	private final int _LeaderOID;
	private final int _memberCount;
	private final String _name;
	
	/**
	 * @param party
	 * @param mode 0 = Remove, 1 = Add
	 */
	public ExMPCCPartyInfoUpdate(Party party, int mode)
	{
		_name = party.getLeader().getName();
		_LeaderOID = party.getLeaderObjectId();
		_memberCount = party.getMemberCount();
		_mode = mode;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MPCCPARTY_INFO_UPDATE.writeId(packet);
		
		packet.writeS(_name);
		packet.writeD(_LeaderOID);
		packet.writeD(_memberCount);
		packet.writeD(_mode); // mode 0 = Remove Party, 1 = AddParty, maybe more...
		return true;
	}
}
