package org.l2jmobius.gameserver.network.clientpackets;

import static org.l2jmobius.gameserver.model.actor.Npc.INTERACTION_DISTANCE;

import java.util.HashSet;
import java.util.Set;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.OfflineTraderTable;
import org.l2jmobius.gameserver.enums.PrivateStoreType;
import org.l2jmobius.gameserver.model.ItemRequest;
import org.l2jmobius.gameserver.model.TradeList;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.ceremonyofchaos.CeremonyOfChaosEvent;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.util.Util;

/**
 * @version $Revision: 1.2.2.1.2.5 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestPrivateStoreBuy implements IClientIncomingPacket
{
	private static final int BATCH_LENGTH = 20; // length of the one item
	
	private int _storePlayerId;
	private Set<ItemRequest> _items = null;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_storePlayerId = packet.readD();
		final int count = packet.readD();
		if ((count <= 0) || (count > Config.MAX_ITEM_IN_PACKET) || ((count * BATCH_LENGTH) != packet.getReadableBytes()))
		{
			return false;
		}
		_items = new HashSet<>();
		for (int i = 0; i < count; i++)
		{
			final int objectId = packet.readD();
			final long cnt = packet.readQ();
			final long price = packet.readQ();
			if ((objectId < 1) || (cnt < 1) || (price < 0))
			{
				_items = null;
				return false;
			}
			
			_items.add(new ItemRequest(objectId, cnt, price));
		}
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
		
		if (_items == null)
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Cannot set private store in Ceremony of Chaos event.
		if (player.isOnEvent(CeremonyOfChaosEvent.class))
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_OPEN_A_PRIVATE_STORE_OR_WORKSHOP_IN_THE_CEREMONY_OF_CHAOS);
			return;
		}
		
		if (player.isOnEvent()) // custom event message
		{
			player.sendMessage("You cannot open a private store while participating in an event.");
			return;
		}
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("privatestorebuy"))
		{
			player.sendMessage("You are buying items too fast.");
			return;
		}
		
		final WorldObject object = World.getInstance().getPlayer(_storePlayerId);
		if ((object == null) || player.isCursedWeaponEquipped())
		{
			return;
		}
		
		final PlayerInstance storePlayer = (PlayerInstance) object;
		if (!player.isInsideRadius3D(storePlayer, INTERACTION_DISTANCE))
		{
			return;
		}
		
		if (player.getInstanceWorld() != storePlayer.getInstanceWorld())
		{
			return;
		}
		
		if (!((storePlayer.getPrivateStoreType() == PrivateStoreType.SELL) || (storePlayer.getPrivateStoreType() == PrivateStoreType.PACKAGE_SELL)))
		{
			return;
		}
		
		final TradeList storeList = storePlayer.getSellList();
		if (storeList == null)
		{
			return;
		}
		
		if (!player.getAccessLevel().allowTransaction())
		{
			player.sendMessage("Transactions are disabled for your Access Level.");
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if ((storePlayer.getPrivateStoreType() == PrivateStoreType.PACKAGE_SELL) && (storeList.getItemCount() > _items.size()))
		{
			final String msgErr = "[RequestPrivateStoreBuy] player " + client.getPlayer().getName() + " tried to buy less items than sold by package-sell, ban this player for bot usage!";
			Util.handleIllegalPlayerAction(client.getPlayer(), msgErr, Config.DEFAULT_PUNISH);
			return;
		}
		
		final int result = storeList.privateStoreBuy(player, _items);
		if (result > 0)
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			if (result > 1)
			{
				LOGGER.warning("PrivateStore buy has failed due to invalid list or request. Player: " + player.getName() + ", Private store of: " + storePlayer.getName());
			}
			return;
		}
		
		// Update offline trade record, if realtime saving is enabled
		if (Config.OFFLINE_TRADE_ENABLE && Config.STORE_OFFLINE_TRADE_IN_REALTIME && ((storePlayer.getClient() == null) || storePlayer.getClient().isDetached()))
		{
			OfflineTraderTable.onTransaction(storePlayer, storeList.getItemCount() == 0, false);
		}
		
		if (storeList.getItemCount() == 0)
		{
			storePlayer.setPrivateStoreType(PrivateStoreType.NONE);
			storePlayer.broadcastUserInfo();
		}
	}
}
