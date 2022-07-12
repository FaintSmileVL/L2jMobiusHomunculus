package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;
import org.l2jmobius.gameserver.util.Util;

/**
 * Lets drink to code!
 * @author zabbix, HorridoJoho
 */
public class RequestLinkHtml implements IClientIncomingPacket
{
	private String _link;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_link = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance actor = client.getPlayer();
		if (actor == null)
		{
			return;
		}
		
		if (_link.isEmpty())
		{
			LOGGER.warning("Player " + actor.getName() + " sent empty html link!");
			return;
		}
		
		if (_link.contains(".."))
		{
			LOGGER.warning("Player " + actor.getName() + " sent invalid html link: link " + _link);
			return;
		}
		
		final int htmlObjectId = actor.validateHtmlAction("link " + _link);
		if (htmlObjectId == -1)
		{
			LOGGER.warning("Player " + actor.getName() + " sent non cached  html link: link " + _link);
			return;
		}
		
		if ((htmlObjectId > 0) && !Util.isInsideRangeOfObjectId(actor, htmlObjectId, Npc.INTERACTION_DISTANCE))
		{
			// No logging here, this could be a common case
			return;
		}
		
		final String filename = "data/html/" + _link;
		final NpcHtmlMessage msg = new NpcHtmlMessage(htmlObjectId);
		msg.setFile(actor, filename);
		actor.sendPacket(msg);
	}
}
