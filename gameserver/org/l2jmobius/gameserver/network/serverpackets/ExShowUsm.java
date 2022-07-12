package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExShowUsm implements IClientOutgoingPacket
{
	public static final ExShowUsm GOD_INTRO = new ExShowUsm(2);
	public static final ExShowUsm SECOND_TRANSFER_QUEST = new ExShowUsm(4);
	public static final ExShowUsm OCTAVIS_INSTANCE_END = new ExShowUsm(6);
	public static final ExShowUsm AWAKENING_END = new ExShowUsm(10);
	public static final ExShowUsm ERTHEIA_FIRST_QUEST = new ExShowUsm(14);
	public static final ExShowUsm USM_Q015_E = new ExShowUsm(15); // Chamber of Prophecies instance
	public static final ExShowUsm ERTHEIA_INTRO_FOR_ERTHEIA = new ExShowUsm(147);
	public static final ExShowUsm ERTHEIA_INTRO_FOR_OTHERS = new ExShowUsm(148);
	
	private final int _videoId;
	
	private ExShowUsm(int videoId)
	{
		_videoId = videoId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_USM.writeId(packet);
		
		packet.writeD(_videoId);
		return true;
	}
}
