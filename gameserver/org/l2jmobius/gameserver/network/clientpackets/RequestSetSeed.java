package org.l2jmobius.gameserver.network.clientpackets;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.Seed;
import org.l2jmobius.gameserver.model.SeedProduction;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

/**
 * @author l3x
 */
public class RequestSetSeed implements IClientIncomingPacket
{
	private static final int BATCH_LENGTH = 20; // length of the one item
	
	private int _manorId;
	private List<SeedProduction> _items;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_manorId = packet.readD();
		final int count = packet.readD();
		if ((count <= 0) || (count > Config.MAX_ITEM_IN_PACKET) || ((count * BATCH_LENGTH) != packet.getReadableBytes()))
		{
			return false;
		}
		
		_items = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			final int itemId = packet.readD();
			final long sales = packet.readQ();
			final long price = packet.readQ();
			if ((itemId < 1) || (sales < 0) || (price < 0))
			{
				_items.clear();
				return false;
			}
			
			if (sales > 0)
			{
				_items.add(new SeedProduction(itemId, sales, price, sales));
			}
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (_items.isEmpty())
		{
			return;
		}
		
		final CastleManorManager manor = CastleManorManager.getInstance();
		if (!manor.isModifiablePeriod())
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Check player privileges
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getClan() == null) || (player.getClan().getCastleId() != _manorId) || !player.hasClanPrivilege(ClanPrivilege.CS_MANOR_ADMIN) || !player.getLastFolkNPC().canInteract(player))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Filter seeds with start amount lower than 0 and incorrect price
		final List<SeedProduction> list = new ArrayList<>(_items.size());
		for (SeedProduction sp : _items)
		{
			final Seed s = manor.getSeed(sp.getId());
			if ((s != null) && (sp.getStartAmount() <= s.getSeedLimit()) && (sp.getPrice() >= s.getSeedMinPrice()) && (sp.getPrice() <= s.getSeedMaxPrice()))
			{
				list.add(sp);
			}
		}
		
		// Save new list
		manor.setNextSeedProduction(list, _manorId);
	}
}
