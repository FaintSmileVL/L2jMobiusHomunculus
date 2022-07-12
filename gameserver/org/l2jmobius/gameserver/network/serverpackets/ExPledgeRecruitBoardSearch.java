package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.entry.PledgeRecruitInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExPledgeRecruitBoardSearch implements IClientOutgoingPacket
{
	final List<PledgeRecruitInfo> _clanList;
	private final int _currentPage;
	private final int _totalNumberOfPage;
	private final int _clanOnCurrentPage;
	private final int _startIndex;
	private final int _endIndex;
	
	static final int CLAN_PER_PAGE = 12;
	
	public ExPledgeRecruitBoardSearch(List<PledgeRecruitInfo> clanList, int currentPage)
	{
		_clanList = clanList;
		_currentPage = currentPage;
		_totalNumberOfPage = (int) Math.ceil((double) _clanList.size() / CLAN_PER_PAGE);
		_startIndex = (_currentPage - 1) * CLAN_PER_PAGE;
		_endIndex = (_startIndex + CLAN_PER_PAGE) > _clanList.size() ? _clanList.size() : _startIndex + CLAN_PER_PAGE;
		_clanOnCurrentPage = _endIndex - _startIndex;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_RECRUIT_BOARD_SEARCH.writeId(packet);
		
		packet.writeD(_currentPage);
		packet.writeD(_totalNumberOfPage);
		packet.writeD(_clanOnCurrentPage);
		
		for (int i = _startIndex; i < _endIndex; i++)
		{
			packet.writeD(_clanList.get(i).getClanId());
			packet.writeD(_clanList.get(i).getClan().getAllyId());
		}
		for (int i = _startIndex; i < _endIndex; i++)
		{
			final Clan clan = _clanList.get(i).getClan();
			packet.writeD(clan.getCrestId());
			packet.writeD(clan.getAllyCrestId());
			packet.writeS(clan.getName());
			packet.writeS(clan.getLeaderName());
			packet.writeD(clan.getLevel());
			packet.writeD(clan.getMembersCount());
			packet.writeD(_clanList.get(i).getKarma());
			packet.writeS(_clanList.get(i).getInformation());
			packet.writeD(_clanList.get(i).getApplicationType()); // Helios
			packet.writeD(_clanList.get(i).getRecruitType()); // Helios
		}
		return true;
	}
}
