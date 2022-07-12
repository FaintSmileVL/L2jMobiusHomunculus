package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid, mrTJO
 */
public class ExShowContactList implements IClientOutgoingPacket
{
	private final Set<String> _contacts;
	
	public ExShowContactList(PlayerInstance player)
	{
		_contacts = player.getContactList().getAllContacts();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RECEIVE_SHOW_POST_FRIEND.writeId(packet);
		
		packet.writeD(_contacts.size());
		_contacts.forEach(packet::writeS);
		return true;
	}
}
