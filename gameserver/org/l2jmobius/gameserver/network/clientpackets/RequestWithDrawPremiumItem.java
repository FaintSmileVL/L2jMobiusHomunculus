package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.PremiumItem;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExGetPremiumItemList;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author Gnacik
 */
public class RequestWithDrawPremiumItem implements IClientIncomingPacket
{
	private int _itemNum;
	private int _charId;
	private long _itemCount;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_itemNum = packet.readD();
		_charId = packet.readD();
		_itemCount = packet.readQ();
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
		else if (_itemCount <= 0)
		{
			return;
		}
		else if (player.getObjectId() != _charId)
		{
			Util.handleIllegalPlayerAction(player, "[RequestWithDrawPremiumItem] Incorrect owner, Player: " + player.getName(), Config.DEFAULT_PUNISH);
			return;
		}
		else if (player.getPremiumItemList().isEmpty())
		{
			Util.handleIllegalPlayerAction(player, "[RequestWithDrawPremiumItem] Player: " + player.getName() + " try to get item with empty list!", Config.DEFAULT_PUNISH);
			return;
		}
		else if ((player.getWeightPenalty() >= 3) || !player.isInventoryUnder90(false))
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_RECEIVE_THE_DIMENSIONAL_ITEM_BECAUSE_YOU_HAVE_EXCEED_YOUR_INVENTORY_WEIGHT_QUANTITY_LIMIT);
			return;
		}
		else if (player.isProcessingTransaction())
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_RECEIVE_A_DIMENSIONAL_ITEM_DURING_AN_EXCHANGE);
			return;
		}
		
		final PremiumItem item = player.getPremiumItemList().get(_itemNum);
		if (item == null)
		{
			return;
		}
		else if (item.getCount() < _itemCount)
		{
			return;
		}
		
		final long itemsLeft = (item.getCount() - _itemCount);
		player.addItem("PremiumItem", item.getItemId(), _itemCount, player.getTarget(), true);
		if (itemsLeft > 0)
		{
			item.updateCount(itemsLeft);
			player.updatePremiumItem(_itemNum, itemsLeft);
		}
		else
		{
			player.getPremiumItemList().remove(_itemNum);
			player.deletePremiumItem(_itemNum);
		}
		
		if (player.getPremiumItemList().isEmpty())
		{
			client.sendPacket(SystemMessageId.THERE_ARE_NO_MORE_DIMENSIONAL_ITEMS_TO_BE_FOUND);
		}
		else
		{
			client.sendPacket(new ExGetPremiumItemList(player));
		}
	}
}
