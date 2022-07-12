package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.Movie;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author St3eT
 */
public class ExStopScenePlayer implements IClientOutgoingPacket
{
	private final Movie _movie;
	
	public ExStopScenePlayer(Movie movie)
	{
		_movie = movie;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_STOP_SCENE_PLAYER.writeId(packet);
		
		packet.writeD(_movie.getClientId());
		return true;
	}
}