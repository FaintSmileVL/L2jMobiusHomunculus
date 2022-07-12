package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author devScarlet, mrTJO
 */
public class ExPlayScene implements IClientOutgoingPacket
{
	public static final ExPlayScene STATIC_PACKET = new ExPlayScene();
	
	private ExPlayScene()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLAY_SCENE.writeId(packet);
		
		return true;
	}
}
