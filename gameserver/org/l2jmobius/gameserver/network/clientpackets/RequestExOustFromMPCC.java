package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * D0 0F 00 5A 00 77 00 65 00 72 00 67 00 00 00
 * @author -Wooden-
 */
public class RequestExOustFromMPCC implements IClientIncomingPacket
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
		final PlayerInstance target = World.getInstance().getPlayer(_name);
		final PlayerInstance player = client.getPlayer();
		if ((target != null) && target.isInParty() && player.isInParty() && player.getParty().isInCommandChannel() && target.getParty().isInCommandChannel() && player.getParty().getCommandChannel().getLeader().equals(player) && player.getParty().getCommandChannel().equals(target.getParty().getCommandChannel()))
		{
			if (player.equals(target))
			{
				return;
			}
			
			target.getParty().getCommandChannel().removeParty(target.getParty());
			
			SystemMessage sm = new SystemMessage(SystemMessageId.YOU_WERE_DISMISSED_FROM_THE_COMMAND_CHANNEL);
			target.getParty().broadcastPacket(sm);
			
			// check if CC has not been canceled
			if (player.getParty().isInCommandChannel())
			{
				sm = new SystemMessage(SystemMessageId.C1_S_PARTY_HAS_BEEN_DISMISSED_FROM_THE_COMMAND_CHANNEL);
				sm.addString(target.getParty().getLeader().getName());
				player.getParty().getCommandChannel().broadcastPacket(sm);
			}
		}
		else
		{
			player.sendPacket(SystemMessageId.YOUR_TARGET_CANNOT_BE_FOUND);
		}
	}
}
