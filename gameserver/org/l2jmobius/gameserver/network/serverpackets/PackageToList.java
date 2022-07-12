package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 * @author UnAfraid, mrTJO
 */
public class PackageToList implements IClientOutgoingPacket
{
	private final Map<Integer, String> _players;
	
	public PackageToList(Map<Integer, String> chars)
	{
		_players = chars;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PACKAGE_TO_LIST.writeId(packet);
		
		packet.writeD(_players.size());
		for (Entry<Integer, String> entry : _players.entrySet())
		{
			packet.writeD(entry.getKey());
			packet.writeS(entry.getValue());
		}
		return true;
	}
}
