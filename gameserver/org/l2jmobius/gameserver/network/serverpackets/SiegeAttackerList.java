package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.model.SiegeClan;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Populates the Siege Attacker List in the SiegeInfo Window<br>
 * <br>
 * c = ca<br>
 * d = CastleID<br>
 * d = unknow (0x00)<br>
 * d = unknow (0x01)<br>
 * d = unknow (0x00)<br>
 * d = Number of Attackers Clans?<br>
 * d = Number of Attackers Clans<br>
 * { //repeats<br>
 * d = ClanID<br>
 * S = ClanName<br>
 * S = ClanLeaderName<br>
 * d = ClanCrestID<br>
 * d = signed time (seconds)<br>
 * d = AllyID<br>
 * S = AllyName<br>
 * S = AllyLeaderName<br>
 * d = AllyCrestID<br>
 * @author KenM
 */
public class SiegeAttackerList implements IClientOutgoingPacket
{
	private final Castle _castle;
	
	public SiegeAttackerList(Castle castle)
	{
		_castle = castle;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.CASTLE_SIEGE_ATTACKER_LIST.writeId(packet);
		
		packet.writeD(_castle.getResidenceId());
		packet.writeD(0x00); // 0
		packet.writeD(0x01); // 1
		packet.writeD(0x00); // 0
		final int size = _castle.getSiege().getAttackerClans().size();
		if (size > 0)
		{
			Clan clan;
			packet.writeD(size);
			packet.writeD(size);
			for (SiegeClan siegeclan : _castle.getSiege().getAttackerClans())
			{
				clan = ClanTable.getInstance().getClan(siegeclan.getClanId());
				if (clan == null)
				{
					continue;
				}
				
				packet.writeD(clan.getId());
				packet.writeS(clan.getName());
				packet.writeS(clan.getLeaderName());
				packet.writeD(clan.getCrestId());
				packet.writeD(0x00); // signed time (seconds) (not storated by L2J)
				packet.writeD(clan.getAllyId());
				packet.writeS(clan.getAllyName());
				packet.writeS(""); // AllyLeaderName
				packet.writeD(clan.getAllyCrestId());
			}
		}
		else
		{
			packet.writeD(0x00);
			packet.writeD(0x00);
		}
		return true;
	}
}
