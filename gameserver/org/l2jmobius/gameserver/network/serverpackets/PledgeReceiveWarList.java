package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanWar;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class PledgeReceiveWarList implements IClientOutgoingPacket
{
	private final Clan _clan;
	private final int _tab;
	private final Collection<ClanWar> _clanList;
	
	public PledgeReceiveWarList(Clan clan, int tab)
	{
		_clan = clan;
		_tab = tab;
		_clanList = clan.getWarList().values();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_RECEIVE_WAR_LIST.writeId(packet);
		
		packet.writeD(_tab); // page
		packet.writeD(_clanList.size());
		for (ClanWar clanWar : _clanList)
		{
			final Clan clan = clanWar.getOpposingClan(_clan);
			if (clan == null)
			{
				continue;
			}
			
			packet.writeS(clan.getName());
			packet.writeD(clanWar.getState().ordinal()); // type: 0 = Declaration, 1 = Blood Declaration, 2 = In War, 3 = Victory, 4 = Defeat, 5 = Tie, 6 = Error
			packet.writeD(clanWar.getRemainingTime()); // Time if friends to start remaining
			packet.writeD(clanWar.getKillDifference(_clan)); // Score
			packet.writeD(0); // @TODO: Recent change in points
			packet.writeD(clanWar.getKillToStart()); // Friends to start war left
		}
		return true;
	}
}
