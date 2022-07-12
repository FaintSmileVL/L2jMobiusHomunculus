package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.Movie;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExStartScenePlayer implements IClientOutgoingPacket
{
	private final Movie _movie;
	
	public ExStartScenePlayer(Movie movie)
	{
		_movie = movie;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_START_SCENE_PLAYER.writeId(packet);
		
		packet.writeD(_movie.getClientId());
		return true;
	}
}
