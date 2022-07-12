package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.VariationData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExPutItemResultForVariationMake;

/**
 * Format:(ch) d
 * @author -Wooden-
 */
public class RequestConfirmTargetItem extends AbstractRefinePacket
{
	private int _itemObjId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
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
		if (item == null)
		{
			return;
		}
		
		if (!VariationData.getInstance().hasFeeData(item.getId()))
		{
			client.sendPacket(SystemMessageId.THIS_IS_NOT_A_SUITABLE_ITEM);
			return;
		}
		
		if (!isValid(player, item))
		{
			// Different system message here
			if (item.isAugmented())
			{
				client.sendPacket(SystemMessageId.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN);
				return;
			}
			
			client.sendPacket(SystemMessageId.THIS_IS_NOT_A_SUITABLE_ITEM);
			return;
		}
		
		client.sendPacket(new ExPutItemResultForVariationMake(_itemObjId, item.getId()));
	}
}
