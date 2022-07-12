package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.enums.SiegeClanType;
import org.l2jmobius.gameserver.model.SiegeClan;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Populates the Siege Defender List in the SiegeInfo Window<br>
 * <br>
 * c = 0xcb<br>
 * d = CastleID<br>
 * d = unknow (0x00)<br>
 * d = unknow (0x01)<br>
 * d = unknow (0x00)<br>
 * d = Number of Defending Clans?<br>
 * d = Number of Defending Clans<br>
 * { //repeats<br>
 * d = ClanID<br>
 * S = ClanName<br>
 * S = ClanLeaderName<br>
 * d = ClanCrestID<br>
 * d = signed time (seconds)<br>
 * d = Type -> Owner = 0x01 || Waiting = 0x02 || Accepted = 0x03<br>
 * d = AllyID<br>
 * S = AllyName<br>
 * S = AllyLeaderName<br>
 * d = AllyCrestID<br>
 * @author KenM
 */
public class SiegeDefenderList implements IClientOutgoingPacket
{
	private final Castle _castle;
	
	public SiegeDefenderList(Castle castle)
	{
		_castle = castle;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CASTLE_SIEGE_DEFENDER_LIST.writeId(packet);
		
		packet.writeD(_castle.getResidenceId());
		packet.writeD(0x00); // Unknown
		packet.writeD(0x01); // Unknown
		packet.writeD(0x00); // Unknown
		
		final int size = _castle.getSiege().getDefenderWaitingClans().size() + _castle.getSiege().getDefenderClans().size() + (_castle.getOwner() != null ? 1 : 0);
		packet.writeD(size);
		packet.writeD(size);
		
		// Add owners
		final Clan ownerClan = _castle.getOwner();
		if (ownerClan != null)
		{
			packet.writeD(ownerClan.getId());
			packet.writeS(ownerClan.getName());
			packet.writeS(ownerClan.getLeaderName());
			packet.writeD(ownerClan.getCrestId());
			packet.writeD(0x00); // signed time (seconds) (not storated by L2J)
			packet.writeD(SiegeClanType.OWNER.ordinal());
			packet.writeD(ownerClan.getAllyId());
			packet.writeS(ownerClan.getAllyName());
			packet.writeS(""); // AllyLeaderName
			packet.writeD(ownerClan.getAllyCrestId());
		}
		
		// List of confirmed defenders
		for (SiegeClan siegeClan : _castle.getSiege().getDefenderClans())
		{
			final Clan defendingClan = ClanTable.getInstance().getClan(siegeClan.getClanId());
			if ((defendingClan == null) || (defendingClan == _castle.getOwner()))
			{
				continue;
			}
			
			packet.writeD(defendingClan.getId());
			packet.writeS(defendingClan.getName());
			packet.writeS(defendingClan.getLeaderName());
			packet.writeD(defendingClan.getCrestId());
			packet.writeD(0x00); // signed time (seconds) (not storated by L2J)
			packet.writeD(SiegeClanType.DEFENDER.ordinal());
			packet.writeD(defendingClan.getAllyId());
			packet.writeS(defendingClan.getAllyName());
			packet.writeS(""); // AllyLeaderName
			packet.writeD(defendingClan.getAllyCrestId());
		}
		
		// List of not confirmed defenders
		for (SiegeClan siegeClan : _castle.getSiege().getDefenderWaitingClans())
		{
			final Clan defendingClan = ClanTable.getInstance().getClan(siegeClan.getClanId());
			if (defendingClan == null)
			{
				continue;
			}
			
			packet.writeD(defendingClan.getId());
			packet.writeS(defendingClan.getName());
			packet.writeS(defendingClan.getLeaderName());
			packet.writeD(defendingClan.getCrestId());
			packet.writeD(0x00); // signed time (seconds) (not storated by L2J)
			packet.writeD(SiegeClanType.DEFENDER_PENDING.ordinal());
			packet.writeD(defendingClan.getAllyId());
			packet.writeS(defendingClan.getAllyName());
			packet.writeS(""); // AllyLeaderName
			packet.writeD(defendingClan.getAllyCrestId());
		}
		return true;
	}
}
