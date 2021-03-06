package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.TradeItem;
import org.l2jmobius.gameserver.model.TradeList;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.TradeOtherAdd;
import org.l2jmobius.gameserver.network.serverpackets.TradeOwnAdd;
import org.l2jmobius.gameserver.network.serverpackets.TradeUpdate;

/**
 * @version $Revision: 1.5.2.2.2.5 $ $Date: 2005/03/27 15:29:29 $
 */
public class AddTradeItem implements IClientIncomingPacket
{
	private int _tradeId;
	private int _objectId;
	private long _count;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_tradeId = packet.readD();
		_objectId = packet.readD();
		_count = packet.readQ();
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
		
		final TradeList trade = player.getActiveTradeList();
		if (trade == null)
		{
			LOGGER.warning("Character: " + player.getName() + " requested item:" + _objectId + " add without active tradelist:" + _tradeId);
			return;
		}
		
		final PlayerInstance partner = trade.getPartner();
		if ((partner == null) || (World.getInstance().getPlayer(partner.getObjectId()) == null) || (partner.getActiveTradeList() == null))
		{
			// Trade partner not found, cancel trade
			if (partner != null)
			{
				LOGGER.warning("Character:" + player.getName() + " requested invalid trade object: " + _objectId);
			}
			player.sendPacket(SystemMessageId.THAT_PLAYER_IS_NOT_ONLINE);
			player.cancelActiveTrade();
			return;
		}
		
		if (trade.isConfirmed() || partner.getActiveTradeList().isConfirmed())
		{
			player.sendPacket(SystemMessageId.YOU_MAY_NO_LONGER_ADJUST_ITEMS_IN_THE_TRADE_BECAUSE_THE_TRADE_HAS_BEEN_CONFIRMED);
			return;
		}
		
		if (!player.getAccessLevel().allowTransaction())
		{
			player.sendMessage("Transactions are disabled for your Access Level.");
			player.cancelActiveTrade();
			return;
		}
		
		if (!player.validateItemManipulation(_objectId, "trade"))
		{
			player.sendPacket(SystemMessageId.NOTHING_HAPPENED);
			return;
		}
		
		final ItemInstance item1 = player.getInventory().getItemByObjectId(_objectId);
		final TradeItem item2 = trade.addItem(_objectId, _count);
		if (item2 != null)
		{
			player.sendPacket(new TradeOwnAdd(1, item2));
			player.sendPacket(new TradeOwnAdd(2, item2));
			player.sendPacket(new TradeUpdate(1, null, null, 0));
			player.sendPacket(new TradeUpdate(2, player, item2, item1.getCount() - item2.getCount()));
			partner.sendPacket(new TradeOtherAdd(1, item2));
			partner.sendPacket(new TradeOtherAdd(2, item2));
		}
	}
}
