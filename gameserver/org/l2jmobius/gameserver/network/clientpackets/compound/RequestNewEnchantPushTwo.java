package org.l2jmobius.gameserver.network.clientpackets.compound;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.CombinationItemsData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.CompoundRequest;
import org.l2jmobius.gameserver.model.items.combination.CombinationItem;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.compound.ExEnchantOneFail;
import org.l2jmobius.gameserver.network.serverpackets.compound.ExEnchantTwoFail;
import org.l2jmobius.gameserver.network.serverpackets.compound.ExEnchantTwoOK;

/**
 * @author UnAfraid
 */
public class RequestNewEnchantPushTwo implements IClientIncomingPacket
{
	private int _objectId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
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
		else if (player.isInStoreMode())
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_DO_THAT_WHILE_IN_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
			client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
			return;
		}
		else if (player.isProcessingTransaction() || player.isProcessingRequest())
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_USE_THIS_SYSTEM_DURING_TRADING_PRIVATE_STORE_AND_WORKSHOP_SETUP);
			client.sendPacket(ExEnchantOneFail.STATIC_PACKET);
			return;
		}
		
		final CompoundRequest request = player.getRequest(CompoundRequest.class);
		if ((request == null) || request.isProcessing())
		{
			client.sendPacket(ExEnchantTwoFail.STATIC_PACKET);
			return;
		}
		
		// Make sure player owns this item.
		request.setItemTwo(_objectId);
		final ItemInstance itemOne = request.getItemOne();
		final ItemInstance itemTwo = request.getItemTwo();
		if ((itemOne == null) || (itemTwo == null))
		{
			client.sendPacket(ExEnchantTwoFail.STATIC_PACKET);
			return;
		}
		
		// Lets prevent using same item twice. Also stackable item check.
		if ((itemOne.getObjectId() == itemTwo.getObjectId()) && (!itemOne.isStackable() || (player.getInventory().getInventoryItemCount(itemOne.getItem().getId(), -1) < 2)))
		{
			client.sendPacket(ExEnchantTwoFail.STATIC_PACKET);
			return;
		}
		
		final CombinationItem combinationItem = CombinationItemsData.getInstance().getItemsBySlots(itemOne.getId(), itemTwo.getId());
		
		// Not implemented or not able to merge!
		if (combinationItem == null)
		{
			client.sendPacket(ExEnchantTwoFail.STATIC_PACKET);
			return;
		}
		
		client.sendPacket(ExEnchantTwoOK.STATIC_PACKET);
	}
}
