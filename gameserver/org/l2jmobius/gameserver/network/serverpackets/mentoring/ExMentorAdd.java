package org.l2jmobius.gameserver.network.serverpackets.mentoring;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Gnacik, UnAfraid
 */
public class ExMentorAdd implements IClientOutgoingPacket
{
	final PlayerInstance _mentor;
	
	public ExMentorAdd(PlayerInstance mentor)
	{
		_mentor = mentor;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MENTOR_ADD.writeId(packet);
		
		packet.writeS(_mentor.getName());
		packet.writeD(_mentor.getActiveClass());
		packet.writeD(_mentor.getLevel());
		return true;
	}
}
