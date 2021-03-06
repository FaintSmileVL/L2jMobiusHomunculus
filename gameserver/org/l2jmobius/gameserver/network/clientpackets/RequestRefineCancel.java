package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.VariationData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExVariationCancelResult;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;
import org.l2jmobius.gameserver.util.Util;

/**
 * Format(ch) d
 * @author -Wooden-
 */
public class RequestRefineCancel implements IClientIncomingPacket
{
	private int _targetItemObjId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_targetItemObjId = packet.readD();
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
		
		final ItemInstance targetItem = player.getInventory().getItemByObjectId(_targetItemObjId);
		if (targetItem == null)
		{
			client.sendPacket(ExVariationCancelResult.STATIC_PACKET_FAILURE);
			return;
		}
		
		if (targetItem.getOwnerId() != player.getObjectId())
		{
			Util.handleIllegalPlayerAction(client.getPlayer(), "Warning!! Character " + client.getPlayer().getName() + " of account " + client.getPlayer().getAccountName() + " tryied to augment item that doesn't own.", Config.DEFAULT_PUNISH);
			return;
		}
		
		// cannot remove augmentation from a not augmented item
		if (!targetItem.isAugmented())
		{
			client.sendPacket(SystemMessageId.AUGMENTATION_REMOVAL_CAN_ONLY_BE_DONE_ON_AN_AUGMENTED_ITEM);
			client.sendPacket(ExVariationCancelResult.STATIC_PACKET_FAILURE);
			return;
		}
		
		// get the price
		final long price = VariationData.getInstance().getCancelFee(targetItem.getId(), targetItem.getAugmentation().getMineralId());
		if (price < 0)
		{
			client.sendPacket(ExVariationCancelResult.STATIC_PACKET_FAILURE);
			return;
		}
		
		// try to reduce the players adena
		if (!player.reduceAdena("RequestRefineCancel", price, targetItem, true))
		{
			client.sendPacket(ExVariationCancelResult.STATIC_PACKET_FAILURE);
			client.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			return;
		}
		
		// unequip item
		final InventoryUpdate iu = new InventoryUpdate();
		if (targetItem.isEquipped())
		{
			final ItemInstance[] unequiped = player.getInventory().unEquipItemInSlotAndRecord(targetItem.getLocationSlot());
			for (ItemInstance itm : unequiped)
			{
				iu.addModifiedItem(itm);
			}
		}
		
		// remove the augmentation
		targetItem.removeAugmentation();
		
		// send ExVariationCancelResult
		client.sendPacket(ExVariationCancelResult.STATIC_PACKET_SUCCESS);
		
		// send inventory update
		iu.addModifiedItem(targetItem);
		player.sendInventoryUpdate(iu);
	}
}
