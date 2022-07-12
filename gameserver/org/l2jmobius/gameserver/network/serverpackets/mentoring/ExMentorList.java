package org.l2jmobius.gameserver.network.serverpackets.mentoring;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.instancemanager.MentorManager;
import org.l2jmobius.gameserver.model.Mentee;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExMentorList implements IClientOutgoingPacket
{
	private final int _type;
	private final Collection<Mentee> _mentees;
	
	public ExMentorList(PlayerInstance player)
	{
		if (player.isMentor())
		{
			_type = 0x01;
			_mentees = MentorManager.getInstance().getMentees(player.getObjectId());
		}
		else if (player.isMentee())
		{
			_type = 0x02;
			_mentees = Arrays.asList(MentorManager.getInstance().getMentor(player.getObjectId()));
		}
		else if (player.isInCategory(CategoryType.SIXTH_CLASS_GROUP)) // Not a mentor, Not a mentee, so can be a mentor
		{
			_mentees = Collections.emptyList();
			_type = 0x01;
		}
		else
		{
			_mentees = Collections.emptyList();
			_type = 0x00;
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MENTOR_LIST.writeId(packet);
		
		packet.writeD(_type);
		packet.writeD(0x00);
		packet.writeD(_mentees.size());
		for (Mentee mentee : _mentees)
		{
			packet.writeD(mentee.getObjectId());
			packet.writeS(mentee.getName());
			packet.writeD(mentee.getClassId());
			packet.writeD(mentee.getLevel());
			packet.writeD(mentee.isOnlineInt());
		}
		return true;
	}
}
