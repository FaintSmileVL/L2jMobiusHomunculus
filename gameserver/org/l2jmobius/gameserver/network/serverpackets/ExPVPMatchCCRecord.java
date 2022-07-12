package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class ExPVPMatchCCRecord implements IClientOutgoingPacket
{
	public static final int INITIALIZE = 0;
	public static final int UPDATE = 1;
	public static final int FINISH = 2;
	
	private final int _state;
	private final Map<PlayerInstance, Integer> _players;
	
	public ExPVPMatchCCRecord(int state, Map<PlayerInstance, Integer> players)
	{
		_state = state;
		_players = players;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PVP_MATCH_CCRECORD.writeId(packet);
		packet.writeD(_state); // 0 - initialize, 1 - update, 2 - finish
		packet.writeD(Math.min(_players.size(), 25));
		int counter = 0;
		for (Entry<PlayerInstance, Integer> entry : _players.entrySet())
		{
			counter++;
			if (counter > 25)
			{
				break;
			}
			packet.writeS(entry.getKey().getName());
			packet.writeD(entry.getValue());
		}
		return true;
	}
}
