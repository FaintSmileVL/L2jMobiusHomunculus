package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.HennaData;
import org.l2jmobius.gameserver.model.PlayerCondOverride;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.HennaEquipList;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author Zoey76
 */
public class RequestHennaEquip implements IClientIncomingPacket
{
	private int _symbolId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_symbolId = packet.readD();
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
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("HennaEquip"))
		{
			return;
		}
		
		final Henna henna = HennaData.getInstance().getHenna(_symbolId);
		if (henna == null)
		{
			LOGGER.warning("Invalid Henna Id: " + _symbolId + " from player " + player);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (henna.isPremium())
		{
			if ((Config.PREMIUM_HENNA_SLOT_ENABLED_FOR_ALL || player.hasPremiumStatus()) && Config.PREMIUM_HENNA_SLOT_ENABLED && (player.getClassId().level() > 1))
			{
				if (player.getHenna(4) != null)
				{
					player.sendPacket(SystemMessageId.NO_SLOT_EXISTS_TO_DRAW_THE_SYMBOL);
					client.sendPacket(ActionFailed.STATIC_PACKET);
					return;
				}
			}
			else
			{
				client.sendPacket(ActionFailed.STATIC_PACKET);
				return;
			}
		}
		else if (player.getHennaEmptySlots() == 0)
		{
			player.sendPacket(SystemMessageId.NO_SLOT_EXISTS_TO_DRAW_THE_SYMBOL);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final long count = player.getInventory().getInventoryItemCount(henna.getDyeItemId(), -1);
		if (henna.isAllowedClass(player.getClassId()) && (count >= henna.getWearCount()) && (player.getAdena() >= henna.getWearFee()) && player.addHenna(henna))
		{
			player.destroyItemByItemId("Henna", henna.getDyeItemId(), henna.getWearCount(), player, true);
			player.getInventory().reduceAdena("Henna", henna.getWearFee(), player, player.getLastFolkNPC());
			final InventoryUpdate iu = new InventoryUpdate();
			iu.addModifiedItem(player.getInventory().getAdenaInstance());
			player.sendInventoryUpdate(iu);
			player.sendPacket(new HennaEquipList(player));
			player.updateSymbolSealSkills();
			player.sendPacket(SystemMessageId.THE_SYMBOL_HAS_BEEN_ADDED);
		}
		else
		{
			player.sendPacket(SystemMessageId.THE_SYMBOL_CANNOT_BE_DRAWN);
			if (!player.canOverrideCond(PlayerCondOverride.ITEM_CONDITIONS) && !henna.isAllowedClass(player.getClassId()))
			{
				Util.handleIllegalPlayerAction(player, "Exploit attempt: Character " + player.getName() + " of account " + player.getAccountName() + " tryed to add a forbidden henna.", Config.DEFAULT_PUNISH);
			}
			client.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}
}
