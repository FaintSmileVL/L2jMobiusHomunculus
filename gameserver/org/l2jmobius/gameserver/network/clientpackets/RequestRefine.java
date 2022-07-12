package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.VariationData;
import org.l2jmobius.gameserver.model.VariationInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.options.Variation;
import org.l2jmobius.gameserver.model.options.VariationFee;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExVariationResult;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;

/**
 * Format:(ch) dddd
 * @author -Wooden-
 */
public class RequestRefine extends AbstractRefinePacket
{
	private int _targetItemObjId;
	private int _mineralItemObjId;
	private long _feeCount;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_targetItemObjId = packet.readD();
		_mineralItemObjId = packet.readD();
		packet.readD(); // _feeItemObjId
		_feeCount = packet.readQ();
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
			return;
		}
		
		final ItemInstance mineralItem = player.getInventory().getItemByObjectId(_mineralItemObjId);
		if (mineralItem == null)
		{
			return;
		}
		
		final VariationFee fee = VariationData.getInstance().getFee(targetItem.getId(), mineralItem.getId());
		if (fee == null)
		{
			return;
		}
		
		final ItemInstance feeItem = player.getInventory().getItemByItemId(fee.getItemId());
		if (feeItem == null)
		{
			return;
		}
		
		if (!isValid(player, targetItem, mineralItem, feeItem, fee))
		{
			player.sendPacket(new ExVariationResult(0, 0, false));
			player.sendPacket(SystemMessageId.AUGMENTATION_FAILED_DUE_TO_INAPPROPRIATE_CONDITIONS);
			return;
		}
		
		// TODO: Update XMLs.
		// if (_feeCount != fee.getItemCount())
		if (_feeCount <= 0)
		{
			player.sendPacket(new ExVariationResult(0, 0, false));
			player.sendPacket(SystemMessageId.AUGMENTATION_FAILED_DUE_TO_INAPPROPRIATE_CONDITIONS);
			return;
		}
		
		final Variation variation = VariationData.getInstance().getVariation(mineralItem.getId());
		if (variation == null)
		{
			player.sendPacket(new ExVariationResult(0, 0, false));
			return;
		}
		
		final VariationInstance augment = VariationData.getInstance().generateRandomVariation(variation, targetItem);
		if (augment == null)
		{
			player.sendPacket(new ExVariationResult(0, 0, false));
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
			player.broadcastUserInfo();
		}
		
		// consume the life stone
		if (!player.destroyItem("RequestRefine", mineralItem, 1, null, false))
		{
			return;
		}
		
		// consume the gemstones
		if (!player.destroyItem("RequestRefine", feeItem, _feeCount, null, false))
		{
			return;
		}
		
		// Remove the augmentation if any (286).
		if (targetItem.isAugmented())
		{
			targetItem.removeAugmentation();
		}
		
		targetItem.setAugmentation(augment, true);
		player.sendPacket(new ExVariationResult(augment.getOption1Id(), augment.getOption2Id(), true));
		iu.addModifiedItem(targetItem);
		player.sendInventoryUpdate(iu);
	}
}
