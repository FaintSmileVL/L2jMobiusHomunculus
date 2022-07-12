package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.Clan.SubPledge;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class PledgeReceiveSubPledgeCreated implements IClientOutgoingPacket
{
	private final SubPledge _subPledge;
	private final Clan _clan;
	
	public PledgeReceiveSubPledgeCreated(SubPledge subPledge, Clan clan)
	{
		_subPledge = subPledge;
		_clan = clan;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_RECEIVE_SUB_PLEDGE_CREATED.writeId(packet);
		
		packet.writeD(0x01);
		packet.writeD(_subPledge.getId());
		packet.writeS(_subPledge.getName());
		packet.writeS(getLeaderName());
		return true;
	}
	
	private String getLeaderName()
	{
		final int LeaderId = _subPledge.getLeaderId();
		if ((_subPledge.getId() == -1) || (LeaderId == 0))
		{
			return "";
		}
		else if (_clan.getClanMember(LeaderId) == null)
		{
			LOGGER.warning("SubPledgeLeader: " + LeaderId + " is missing from clan: " + _clan.getName() + "[" + _clan.getId() + "]");
			return "";
		}
		else
		{
			return _clan.getClanMember(LeaderId).getName();
		}
	}
}
