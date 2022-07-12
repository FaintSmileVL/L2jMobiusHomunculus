package org.l2jmobius.gameserver.network.clientpackets.homunculus;

import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomonculusBirthInfo;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomonculusCreateStartResult;

/**
 * @author Mobius
 */
public class ExHomunculusCreateStart implements IClientIncomingPacket
{
	private static final int COST = 1000000;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
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
		
		final int status = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_STATUS, 0);
		if (status > 0)
		{
			return;
		}
		
		if (player.getAdena() < COST)
		{
			player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA_2);
			return;
		}
		player.reduceAdena("Homunculus creation", COST, player, true);
		player.getVariables().set(PlayerVariables.HOMUNCULUS_STATUS, 1);
		player.getVariables().set(PlayerVariables.HOMUNCULUS_TIME, Chronos.currentTimeMillis() / 1000);
		
		client.sendPacket(new ExHomonculusBirthInfo(player));
		client.sendPacket(new ExHomonculusCreateStartResult(player));
	}
}
