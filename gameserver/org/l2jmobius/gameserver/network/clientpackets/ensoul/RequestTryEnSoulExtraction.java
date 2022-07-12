package org.l2jmobius.gameserver.network.clientpackets.ensoul;

import java.util.Collection;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.EnsoulData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.ensoul.EnsoulOption;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;
import org.l2jmobius.gameserver.network.serverpackets.ensoul.ExEnSoulExtractionResult;

/**
 * @author Mobius
 */
public class RequestTryEnSoulExtraction implements IClientIncomingPacket
{
	private int _itemObjectId;
	private int _type;
	private int _position;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_itemObjectId = packet.readD();
		_type = packet.readC();
		_position = packet.readC() - 1;
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
		
		final ItemInstance item = player.getInventory().getItemByObjectId(_itemObjectId);
		if (item == null)
		{
			return;
		}
		
		EnsoulOption option = null;
		if (_type == 1)
		{
			option = item.getSpecialAbility(_position);
			// If position is invalid, check the other one.
			if ((option == null) && (_position == 0))
			{
				option = item.getSpecialAbility(1);
				if (option != null)
				{
					_position = 1;
				}
			}
		}
		if (_type == 2)
		{
			option = item.getAdditionalSpecialAbility(_position);
		}
		if (option == null)
		{
			return;
		}
		
		final Collection<ItemHolder> removalFee = EnsoulData.getInstance().getRemovalFee(item.getItem().getCrystalType());
		if (removalFee.isEmpty())
		{
			return;
		}
		
		// Check if player has required items.
		for (ItemHolder itemHolder : removalFee)
		{
			if (player.getInventory().getInventoryItemCount(itemHolder.getId(), -1) < itemHolder.getCount())
			{
				player.sendPacket(SystemMessageId.INCORRECT_ITEM_COUNT);
				player.sendPacket(new ExEnSoulExtractionResult(false, item));
				return;
			}
		}
		
		// Take required items.
		for (ItemHolder itemHolder : removalFee)
		{
			player.destroyItemByItemId("Rune Extract", itemHolder.getId(), itemHolder.getCount(), player, true);
		}
		
		// Remove equipped rune.
		item.removeSpecialAbility(_position, _type);
		final InventoryUpdate iu = new InventoryUpdate();
		iu.addModifiedItem(item);
		
		// Add rune in player inventory.
		final int runeId = EnsoulData.getInstance().getStone(_type, option.getId());
		if (runeId > 0)
		{
			iu.addItem(player.addItem("Rune Extract", runeId, 1, player, true));
		}
		
		player.sendInventoryUpdate(iu);
		player.sendPacket(new ExEnSoulExtractionResult(true, item));
	}
}