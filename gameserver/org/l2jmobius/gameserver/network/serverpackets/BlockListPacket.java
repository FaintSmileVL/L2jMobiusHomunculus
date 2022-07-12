package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.CharNameTable;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class BlockListPacket implements IClientOutgoingPacket
{
	private final List<Integer> _playersId;
	
	public BlockListPacket(List<Integer> playersId)
	{
		_playersId = playersId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.BLOCK_LIST.writeId(packet);
		
		packet.writeD(_playersId.size());
		for (int playerId : _playersId)
		{
			packet.writeS(CharNameTable.getInstance().getNameById(playerId));
			packet.writeS(""); // memo ?
		}
		return true;
	}
}
