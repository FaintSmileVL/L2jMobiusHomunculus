package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.olympiad.OlympiadInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExOlympiadMatchResult implements IClientOutgoingPacket
{
	private final boolean _tie;
	private int _winTeam; // 1,2
	private int _loseTeam = 2;
	private final List<OlympiadInfo> _winnerList;
	private final List<OlympiadInfo> _loserList;
	
	public ExOlympiadMatchResult(boolean tie, int winTeam, List<OlympiadInfo> winnerList, List<OlympiadInfo> loserList)
	{
		_tie = tie;
		_winTeam = winTeam;
		_winnerList = winnerList;
		_loserList = loserList;
		if (_winTeam == 2)
		{
			_loseTeam = 1;
		}
		else if (_winTeam == 0)
		{
			_winTeam = 1;
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RECEIVE_OLYMPIAD.writeId(packet);
		
		packet.writeD(0x01); // Type 0 = Match List, 1 = Match Result
		
		packet.writeD(_tie ? 1 : 0); // 0 - win, 1 - tie
		packet.writeS(_winnerList.get(0).getName());
		packet.writeD(_winTeam);
		packet.writeD(_winnerList.size());
		for (OlympiadInfo info : _winnerList)
		{
			packet.writeS(info.getName());
			packet.writeS(info.getClanName());
			packet.writeD(info.getClanId());
			packet.writeD(info.getClassId());
			packet.writeD(info.getDamage());
			packet.writeD(info.getCurrentPoints());
			packet.writeD(info.getDiffPoints());
			packet.writeD(0x00); // Helios
		}
		
		packet.writeD(_loseTeam);
		packet.writeD(_loserList.size());
		for (OlympiadInfo info : _loserList)
		{
			packet.writeS(info.getName());
			packet.writeS(info.getClanName());
			packet.writeD(info.getClanId());
			packet.writeD(info.getClassId());
			packet.writeD(info.getDamage());
			packet.writeD(info.getCurrentPoints());
			packet.writeD(info.getDiffPoints());
			packet.writeD(0x00); // Helios
		}
		return true;
	}
}
