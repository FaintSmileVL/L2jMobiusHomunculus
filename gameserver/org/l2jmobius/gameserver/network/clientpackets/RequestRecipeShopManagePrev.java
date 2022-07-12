package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.RecipeShopSellList;

/**
 * @version $Revision: 1.1.2.1.2.2 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestRecipeShopManagePrev implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null))
		{
			return;
		}
		
		if (player.isAlikeDead() || (player.getTarget() == null) || !player.getTarget().isPlayer())
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.calculateDistance2D(player.getTarget()) > 250)
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.sendPacket(new RecipeShopSellList(player, player.getTarget().getActingPlayer()));
	}
}
