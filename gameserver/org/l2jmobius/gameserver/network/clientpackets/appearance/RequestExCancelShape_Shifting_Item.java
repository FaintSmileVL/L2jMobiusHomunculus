package org.l2jmobius.gameserver.network.clientpackets.appearance;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.ShapeShiftingItemRequest;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.appearance.ExShapeShiftingResult;

/**
 * @author UnAfraid
 */
public class RequestExCancelShape_Shifting_Item implements IClientIncomingPacket
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
		if (player == null)
		{
			return;
		}
		
		player.removeRequest(ShapeShiftingItemRequest.class);
		client.sendPacket(ExShapeShiftingResult.FAILED);
	}
}
