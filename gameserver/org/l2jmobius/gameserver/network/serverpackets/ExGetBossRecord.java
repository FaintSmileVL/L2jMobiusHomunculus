package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExGetBossRecord implements IClientOutgoingPacket
{
	private final Map<Integer, Integer> _bossRecordInfo;
	private final int _ranking;
	private final int _totalPoints;
	
	public ExGetBossRecord(int ranking, int totalScore, Map<Integer, Integer> list)
	{
		_ranking = ranking;
		_totalPoints = totalScore;
		_bossRecordInfo = list;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_GET_BOSS_RECORD.writeId(packet);
		
		packet.writeD(_ranking);
		packet.writeD(_totalPoints);
		if (_bossRecordInfo == null)
		{
			packet.writeD(0x00);
			packet.writeD(0x00);
			packet.writeD(0x00);
			packet.writeD(0x00);
		}
		else
		{
			packet.writeD(_bossRecordInfo.size()); // list size
			for (Entry<Integer, Integer> entry : _bossRecordInfo.entrySet())
			{
				packet.writeD(entry.getKey());
				packet.writeD(entry.getValue());
				packet.writeD(0x00); // ??
			}
		}
		return true;
	}
}
