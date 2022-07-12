package org.l2jmobius.gameserver.network.clientpackets.sayune;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SayuneData;
import org.l2jmobius.gameserver.enums.CategoryType;
import org.l2jmobius.gameserver.instancemanager.ZoneManager;
import org.l2jmobius.gameserver.model.SayuneEntry;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.SayuneRequest;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.type.SayuneZone;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;

/**
 * @author UnAfraid
 */
public class RequestFlyMoveStart implements IClientIncomingPacket
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
		if ((player == null) || !player.isInsideZone(ZoneId.SAYUNE) || player.hasRequest(SayuneRequest.class) || (!player.isInCategory(CategoryType.SIXTH_CLASS_GROUP) && !Config.FREE_JUMPS_FOR_ALL))
		{
			return;
		}
		
		if (player.hasSummon())
		{
			player.sendPacket(SystemMessageId.YOU_MAY_NOT_USE_SAYUNE_WHILE_PET_OR_SUMMONED_PET_IS_OUT);
			return;
		}
		
		if (player.getReputation() < 0)
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_USE_SAYUNE_WHILE_IN_A_CHAOTIC_STATE);
			return;
		}
		
		if (player.hasRequests())
		{
			player.sendPacket(SystemMessageId.SAYUNE_CANNOT_BE_USED_WHILE_TAKING_OTHER_ACTIONS);
			return;
		}
		
		final SayuneZone zone = ZoneManager.getInstance().getZone(player, SayuneZone.class);
		if (zone.getMapId() == -1)
		{
			player.sendMessage("That zone is not supported yet!");
			LOGGER.warning(getClass().getSimpleName() + ": " + player + " Requested sayune on zone with no map id set");
			return;
		}
		
		final SayuneEntry map = SayuneData.getInstance().getMap(zone.getMapId());
		if (map == null)
		{
			player.sendMessage("This zone is not handled yet!!");
			LOGGER.warning(getClass().getSimpleName() + ": " + player + " Requested sayune on unhandled map zone " + zone.getName());
			return;
		}
		
		final SayuneRequest request = new SayuneRequest(player, map.getId());
		if (player.addRequest(request))
		{
			request.move(player, 0);
		}
	}
}
