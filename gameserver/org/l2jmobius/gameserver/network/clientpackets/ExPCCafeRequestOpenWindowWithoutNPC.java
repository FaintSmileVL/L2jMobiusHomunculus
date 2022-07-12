package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Mobius
 */
public class ExPCCafeRequestOpenWindowWithoutNPC implements IClientIncomingPacket
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
		if ((player != null) && Config.PC_CAFE_ENABLED)
		{
			final NpcHtmlMessage html = new NpcHtmlMessage();
			html.setFile(player, "data/html/pccafe.htm");
			player.sendPacket(html);
		}
	}
}
