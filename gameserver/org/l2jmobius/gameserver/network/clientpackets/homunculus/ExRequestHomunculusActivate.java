package org.l2jmobius.gameserver.network.clientpackets.homunculus;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomonculusActivateResult;

/**
 * @author Mobius
 */
public class ExRequestHomunculusActivate implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readD();
		packet.readC(); // enabled?
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		client.sendPacket(new ExHomonculusActivateResult(player));
	}
}
