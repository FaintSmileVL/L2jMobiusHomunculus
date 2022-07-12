package org.l2jmobius.gameserver.network.clientpackets.homunculus;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomonculusBirthInfo;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomonculusList;

/**
 * @author Mobius
 */
public class ExHomunculusDeleteData implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readD(); // Position?
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
		
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_STATUS);
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_TIME);
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_HP);
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_SP);
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_VP);
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_ID);
		player.getVariables().remove(PlayerVariables.HOMUNCULUS_QUALITY);
		
		player.calculateHomunculusBonuses();
		player.getStat().recalculateStats(true);
		
		client.sendPacket(new ExHomonculusList(player));
		client.sendPacket(new ExHomonculusBirthInfo(player));
	}
}
