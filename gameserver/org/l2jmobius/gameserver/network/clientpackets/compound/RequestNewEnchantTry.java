package org.l2jmobius.gameserver.network.clientpackets.compound;

import network.PacketReader;
import util.Rnd;
import org.l2jmobius.gameserver.data.xml.CombinationItemsData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.CompoundRequest;
import org.l2jmobius.gameserver.model.items.combination.CombinationItem;
import org.l2jmobius.gameserver.model.items.combination.CombinationItemReward;
import org.l2jmobius.gameserver.model.items.combination.CombinationItemType;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;
import org.l2jmobius.gameserver.network.serverpackets.compound.ExEnchantFail;
import org.l2jmobius.gameserver.network.serverpackets.compound.ExEnchantOneFail;
import org.l2jmobius.gameserver.network.serverpackets.compound.ExEnchantSucess;

/**
 * @author UnAfraid
 */
public class RequestNewEnchantTry implements IClientIncomingPacket
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
			client.sendPacket(ExEnchantFail.STATIC_PACKET);
			return;
		}
		
		request.setProcessing(true);
		
		final ItemInstance itemOne = request.getItemOne();
		final ItemInstance itemTwo = request.getItemTwo();
		if ((itemOne == null) || (itemTwo == null))
		{
			client.sendPacket(ExEnchantFail.STATIC_PACKET);
			player.removeRequest(request.getClass());
			return;
		}
		
		// Lets prevent using same item twice. Also stackable item check.
		if ((itemOne.getObjectId() == itemTwo.getObjectId()) && (!itemOne.isStackable() || (player.getInventory().getInventoryItemCount(itemOne.getItem().getId(), -1) < 2)))
		{
			client.sendPacket(new ExEnchantFail(itemOne.getId(), itemTwo.getId()));
			player.removeRequest(request.getClass());
			return;
		}
		
		final CombinationItem combinationItem = CombinationItemsData.getInstance().getItemsBySlots(itemOne.getId(), itemTwo.getId());
		
		// Not implemented or not able to merge!
		if (combinationItem == null)
		{
			client.sendPacket(new ExEnchantFail(itemOne.getId(), itemTwo.getId()));
			player.removeRequest(request.getClass());
			return;
		}
		
		final InventoryUpdate iu = new InventoryUpdate();
		iu.addRemovedItem(itemOne);
		iu.addRemovedItem(itemTwo);
		
		if (player.destroyItem("Compound-Item-One", itemOne, 1, null, true) && player.destroyItem("Compound-Item-Two", itemTwo, 1, null, true))
		{
			final double random = (Rnd.nextDouble() * 100);
			final boolean success = random <= combinationItem.getChance();
			final CombinationItemReward rewardItem = combinationItem.getReward(success ? CombinationItemType.ON_SUCCESS : CombinationItemType.ON_FAILURE);
			final ItemInstance item = player.addItem("Compound-Result", rewardItem.getId(), rewardItem.getCount(), null, true);
			if (success)
			{
				client.sendPacket(new ExEnchantSucess(item.getId()));
			}
			else
			{
				client.sendPacket(new ExEnchantFail(itemOne.getId(), itemTwo.getId()));
			}
		}
		
		player.sendInventoryUpdate(iu);
		player.removeRequest(request.getClass());
	}
}
