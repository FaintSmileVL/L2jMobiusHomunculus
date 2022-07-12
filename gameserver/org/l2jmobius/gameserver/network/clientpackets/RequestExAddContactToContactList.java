package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExConfirmAddingContact;

/**
 * Format: (ch)S S: Character Name
 * @author UnAfraid & mrTJO
 */
public class RequestExAddContactToContactList implements IClientIncomingPacket
{
	private String _name;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_name = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (!Config.ALLOW_MAIL)
		{
			return;
		}
		
		if (_name == null)
		{
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		final boolean charAdded = player.getContactList().add(_name);
		player.sendPacket(new ExConfirmAddingContact(_name, charAdded));
	}
}
