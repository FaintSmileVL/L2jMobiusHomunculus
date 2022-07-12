package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgeStatusChanged implements IClientOutgoingPacket
{
	private final Clan _clan;
	
	public PledgeStatusChanged(Clan clan)
	{
		_clan = clan;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_STATUS_CHANGED.writeId(packet);
		
		packet.writeD(0x00);
		packet.writeD(_clan.getLeaderId());
		packet.writeD(_clan.getId());
		packet.writeD(_clan.getCrestId());
		packet.writeD(_clan.getAllyId());
		packet.writeD(_clan.getAllyCrestId());
		packet.writeD(_clan.getCrestLargeId());
		packet.writeD(0x00); // pledge type ?
		return true;
	}
}
