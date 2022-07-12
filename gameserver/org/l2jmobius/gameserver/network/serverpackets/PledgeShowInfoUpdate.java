package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgeShowInfoUpdate implements IClientOutgoingPacket
{
	private final Clan _clan;
	
	public PledgeShowInfoUpdate(Clan clan)
	{
		_clan = clan;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SHOW_INFO_UPDATE.writeId(packet);
		
		// sending empty data so client will ask all the info in response ;)
		packet.writeD(_clan.getId());
		packet.writeD(Config.SERVER_ID);
		packet.writeD(_clan.getCrestId());
		packet.writeD(_clan.getLevel()); // clan level
		packet.writeD(_clan.getCastleId());
		packet.writeD(0x00); // castle state ?
		packet.writeD(_clan.getHideoutId());
		packet.writeD(_clan.getFortId());
		packet.writeD(_clan.getRank());
		packet.writeD(_clan.getReputationScore()); // clan reputation score
		packet.writeD(0x00); // ?
		packet.writeD(0x00); // ?
		packet.writeD(_clan.getAllyId());
		packet.writeS(_clan.getAllyName()); // c5
		packet.writeD(_clan.getAllyCrestId()); // c5
		packet.writeD(_clan.isAtWar() ? 1 : 0); // c5
		packet.writeD(0x00); // TODO: Find me!
		packet.writeD(0x00); // TODO: Find me!
		return true;
	}
}
