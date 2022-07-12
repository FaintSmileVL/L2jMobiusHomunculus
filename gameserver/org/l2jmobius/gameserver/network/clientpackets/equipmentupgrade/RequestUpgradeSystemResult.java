package org.l2jmobius.gameserver.network.clientpackets.equipmentupgrade;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.EquipmentUpgradeData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.EquipmentUpgradeHolder;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.equipmentupgrade.ExUpgradeSystemResult;

/**
 * @author Mobius
 */
public class RequestUpgradeSystemResult implements IClientIncomingPacket
{
	private int _objectId;
	private int _upgradeId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		_upgradeId = packet.readD();
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
		
		final ItemInstance existingItem = player.getInventory().getItemByObjectId(_objectId);
		if (existingItem == null)
		{
			player.sendPacket(new ExUpgradeSystemResult(0, 0));
			return;
		}
		
		final EquipmentUpgradeHolder upgradeHolder = EquipmentUpgradeData.getInstance().getUpgrade(_upgradeId);
		if (upgradeHolder == null)
		{
			player.sendPacket(new ExUpgradeSystemResult(0, 0));
			return;
		}
		
		for (ItemHolder material : upgradeHolder.getMaterials())
		{
			if (player.getInventory().getInventoryItemCount(material.getId(), -1) < material.getCount())
			{
				player.sendPacket(new ExUpgradeSystemResult(0, 0));
				return;
			}
		}
		
		final long adena = upgradeHolder.getAdena();
		if ((adena > 0) && (player.getAdena() < adena))
		{
			player.sendPacket(new ExUpgradeSystemResult(0, 0));
			return;
		}
		
		if ((existingItem.getItem().getId() != upgradeHolder.getRequiredItemId()) || (existingItem.getEnchantLevel() != upgradeHolder.getRequiredItemEnchant()))
		{
			player.sendPacket(new ExUpgradeSystemResult(0, 0));
			return;
		}
		
		// Get materials.
		player.destroyItem("UpgradeEquipment", _objectId, 1, player, true);
		for (ItemHolder material : upgradeHolder.getMaterials())
		{
			player.destroyItemByItemId("UpgradeEquipment", material.getId(), material.getCount(), player, true);
		}
		if (adena > 0)
		{
			player.reduceAdena("UpgradeEquipment", adena, player, true);
		}
		
		// Give item.
		final ItemInstance newItem = player.addItem("UpgradeEquipment", upgradeHolder.getResultItemId(), 1, player, true);
		final int enchantLevel = upgradeHolder.getResultItemEnchant();
		if (enchantLevel > 0)
		{
			newItem.setEnchantLevel(enchantLevel);
		}
		
		player.sendPacket(new ExUpgradeSystemResult(newItem.getObjectId(), 1));
	}
}
