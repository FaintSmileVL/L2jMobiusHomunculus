package org.l2jmobius.gameserver.network.clientpackets.attributechange;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.enums.AttributeType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.itemcontainer.PlayerInventory;
import org.l2jmobius.gameserver.model.items.enchant.attribute.AttributeHolder;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.network.serverpackets.attributechange.ExChangeAttributeFail;
import org.l2jmobius.gameserver.network.serverpackets.attributechange.ExChangeAttributeOk;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author Mobius
 */
public class RequestChangeAttributeItem implements IClientIncomingPacket
{
	private int _consumeItemId;
	private int _itemObjId;
	private int _newElementId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_consumeItemId = packet.readD();
		_itemObjId = packet.readD();
		_newElementId = packet.readD();
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
		
		final PlayerInventory inventory = player.getInventory();
		final ItemInstance item = inventory.getItemByObjectId(_itemObjId);
		
		// attempting to destroy item
		if (player.getInventory().destroyItemByItemId("ChangeAttribute", _consumeItemId, 1, player, item) == null)
		{
			client.sendPacket(SystemMessageId.INCORRECT_ITEM_COUNT_2);
			client.sendPacket(ExChangeAttributeFail.STATIC);
			Util.handleIllegalPlayerAction(player, "Player " + player.getName() + " tried to change attribute without an attribute change crystal.", Config.DEFAULT_PUNISH);
			return;
		}
		
		// get values
		final int oldElementId = item.getAttackAttributeType().getClientId();
		final int elementValue = item.getAttackAttribute().getValue();
		item.clearAllAttributes();
		item.setAttribute(new AttributeHolder(AttributeType.findByClientId(_newElementId), elementValue), true);
		
		// send packets
		final SystemMessage msg = new SystemMessage(SystemMessageId.S1_S_S2_ATTRIBUTE_HAS_SUCCESSFULLY_CHANGED_TO_S3_ATTRIBUTE);
		msg.addItemName(item);
		msg.addAttribute(oldElementId);
		msg.addAttribute(_newElementId);
		player.sendPacket(msg);
		final InventoryUpdate iu = new InventoryUpdate();
		iu.addModifiedItem(item);
		for (ItemInstance i : player.getInventory().getItemsByItemId(_consumeItemId))
		{
			iu.addItem(i);
		}
		player.sendPacket(iu);
		player.broadcastUserInfo();
		player.sendPacket(ExChangeAttributeOk.STATIC);
	}
}
