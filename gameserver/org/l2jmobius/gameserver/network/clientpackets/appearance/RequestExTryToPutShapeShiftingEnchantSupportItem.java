package org.l2jmobius.gameserver.network.clientpackets.appearance;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.AppearanceItemData;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.ShapeShiftingItemRequest;
import org.l2jmobius.gameserver.model.itemcontainer.PlayerInventory;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceStone;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceType;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.appearance.ExPutShapeShiftingExtractionItemResult;
import org.l2jmobius.gameserver.network.serverpackets.appearance.ExPutShapeShiftingTargetItemResult;

/**
 * @author UnAfraid
 */
public class RequestExTryToPutShapeShiftingEnchantSupportItem implements IClientIncomingPacket
{
	private int _targetItemObjId;
	private int _extracItemObjId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_targetItemObjId = packet.readD();
		_extracItemObjId = packet.readD();
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
		
		final ShapeShiftingItemRequest request = player.getRequest(ShapeShiftingItemRequest.class);
		if (player.isInStoreMode() || player.isCrafting() || player.isProcessingRequest() || player.isProcessingTransaction() || (request == null))
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_USE_THIS_SYSTEM_DURING_TRADING_PRIVATE_STORE_AND_WORKSHOP_SETUP);
			return;
		}
		
		final PlayerInventory inventory = player.getInventory();
		final ItemInstance targetItem = inventory.getItemByObjectId(_targetItemObjId);
		final ItemInstance extractItem = inventory.getItemByObjectId(_extracItemObjId);
		ItemInstance stone = request.getAppearanceStone();
		if ((targetItem == null) || (extractItem == null) || (stone == null))
		{
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if ((stone.getOwnerId() != player.getObjectId()) || (targetItem.getOwnerId() != player.getObjectId()) || (extractItem.getOwnerId() != player.getObjectId()))
		{
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if (!extractItem.getItem().isAppearanceable())
		{
			player.sendPacket(SystemMessageId.THIS_ITEM_DOES_NOT_MEET_REQUIREMENTS);
			client.sendPacket(ExPutShapeShiftingExtractionItemResult.FAILED);
			return;
		}
		
		if ((extractItem.getItemLocation() != ItemLocation.INVENTORY) && (extractItem.getItemLocation() != ItemLocation.PAPERDOLL))
		{
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if ((stone = inventory.getItemByObjectId(stone.getObjectId())) == null)
		{
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		final AppearanceStone appearanceStone = AppearanceItemData.getInstance().getStone(stone.getId());
		if (appearanceStone == null)
		{
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if ((appearanceStone.getType() == AppearanceType.RESTORE) || (appearanceStone.getType() == AppearanceType.FIXED))
		{
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if (extractItem.getVisualId() > 0)
		{
			client.sendPacket(ExPutShapeShiftingExtractionItemResult.FAILED);
			client.sendPacket(SystemMessageId.YOU_CANNOT_EXTRACT_FROM_A_MODIFIED_ITEM);
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if ((extractItem.getItemLocation() != ItemLocation.INVENTORY) && (extractItem.getItemLocation() != ItemLocation.PAPERDOLL))
		{
			client.sendPacket(ExPutShapeShiftingExtractionItemResult.FAILED);
			player.removeRequest(ShapeShiftingItemRequest.class);
			return;
		}
		
		if ((extractItem.getItemType() != targetItem.getItemType()) || (extractItem.getId() == targetItem.getId()) || (extractItem.getObjectId() == targetItem.getObjectId()))
		{
			player.sendPacket(SystemMessageId.THIS_ITEM_DOES_NOT_MEET_REQUIREMENTS);
			client.sendPacket(ExPutShapeShiftingExtractionItemResult.FAILED);
			return;
		}
		
		if ((extractItem.getItem().getBodyPart() != targetItem.getItem().getBodyPart()) && ((extractItem.getItem().getBodyPart() != Item.SLOT_FULL_ARMOR) || (targetItem.getItem().getBodyPart() != Item.SLOT_CHEST)))
		{
			player.sendPacket(SystemMessageId.THIS_ITEM_DOES_NOT_MEET_REQUIREMENTS);
			client.sendPacket(ExPutShapeShiftingExtractionItemResult.FAILED);
			return;
		}
		
		if (extractItem.getItem().getCrystalType().isGreater(targetItem.getItem().getCrystalType()))
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_EXTRACT_FROM_ITEMS_THAT_ARE_HIGHER_GRADE_THAN_ITEMS_TO_BE_MODIFIED);
			player.sendPacket(ExPutShapeShiftingExtractionItemResult.FAILED);
			return;
		}
		
		if (!appearanceStone.checkConditions(player, targetItem))
		{
			player.sendPacket(ExPutShapeShiftingTargetItemResult.FAILED);
			return;
		}
		
		request.setAppearanceExtractItem(extractItem);
		client.sendPacket(ExPutShapeShiftingExtractionItemResult.SUCCESS);
	}
}
