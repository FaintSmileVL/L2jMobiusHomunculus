package org.l2jmobius.gameserver.network.clientpackets.attributechange;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.attributechange.ExChangeAttributeInfo;

/**
 * @author Mobius
 */
public class SendChangeAttributeTargetItem implements IClientIncomingPacket
{
	private int _crystalItemId;
	private int _itemObjId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_crystalItemId = packet.readD();
		_itemObjId = packet.readD();
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
		
		final ItemInstance item = player.getInventory().getItemByObjectId(_itemObjId);
		if ((item == null) || !item.isWeapon())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.sendPacket(new ExChangeAttributeInfo(_crystalItemId, item));
	}
}
