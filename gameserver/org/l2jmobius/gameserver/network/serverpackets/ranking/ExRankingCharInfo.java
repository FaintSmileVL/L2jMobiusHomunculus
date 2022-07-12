package org.l2jmobius.gameserver.network.serverpackets.ranking;

import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.RankManager;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author NviX
 */
public class ExRankingCharInfo implements IClientOutgoingPacket
{
	@SuppressWarnings("unused")
	private final short _unk;
	private final PlayerInstance _player;
	private final Map<Integer, StatSet> _playerList;
	private final Map<Integer, StatSet> _snapshotList;
	
	public ExRankingCharInfo(PlayerInstance player, short unk)
	{
		_unk = unk;
		_player = player;
		_playerList = RankManager.getInstance().getRankList();
		_snapshotList = RankManager.getInstance().getSnapshotList();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RANKING_CHAR_INFO.writeId(packet);
		
		if (_playerList.size() > 0)
		{
			for (Integer id : _playerList.keySet())
			{
				final StatSet player = _playerList.get(id);
				if (player.getInt("charId") == _player.getObjectId())
				{
					packet.writeD(id); // server rank
					packet.writeD(player.getInt("raceRank")); // race rank
					
					for (Integer id2 : _snapshotList.keySet())
					{
						final StatSet snapshot = _snapshotList.get(id2);
						if (player.getInt("charId") == snapshot.getInt("charId"))
						{
							packet.writeD(id2); // server rank snapshot
							packet.writeD(snapshot.getInt("raceRank")); // race rank snapshot
							return true;
						}
					}
				}
			}
			packet.writeD(0); // server rank
			packet.writeD(0); // race rank
			packet.writeD(0); // server rank snapshot
			packet.writeD(0); // race rank snapshot
			packet.writeD(0); // nClassRank
			packet.writeD(0); // nClassRank_Snapshot snapshot
		}
		else
		{
			packet.writeD(0); // server rank
			packet.writeD(0); // race rank
			packet.writeD(0); // server rank snapshot
			packet.writeD(0); // race rank snapshot
			packet.writeD(0); // nClassRank
			packet.writeD(0); // nClassRank_Snapshot snapshot
		}
		return true;
	}
}
