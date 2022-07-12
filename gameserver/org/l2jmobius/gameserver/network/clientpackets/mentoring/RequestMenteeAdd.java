package org.l2jmobius.gameserver.network.clientpackets.mentoring;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.network.serverpackets.mentoring.ExMentorAdd;

/**
 * @author Gnacik, UnAfraid
 */
public class RequestMenteeAdd implements IClientIncomingPacket
{
	private String _target;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_target = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance mentor = client.getPlayer();
		if (mentor == null)
		{
			return;
		}
		
		final PlayerInstance mentee = World.getInstance().getPlayer(_target);
		if (mentee == null)
		{
			return;
		}
		
		if (ConfirmMenteeAdd.validate(mentor, mentee))
		{
			mentor.sendPacket(new SystemMessage(SystemMessageId.YOU_HAVE_OFFERED_TO_BECOME_S1_S_MENTOR).addString(mentee.getName()));
			mentee.sendPacket(new ExMentorAdd(mentor));
		}
	}
}