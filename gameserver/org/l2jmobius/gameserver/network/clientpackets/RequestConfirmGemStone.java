package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.VariationData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.options.VariationFee;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExPutCommissionResultForVariationMake;

/**
 * Format:(ch) dddd
 * @author -Wooden-
 */
public class RequestConfirmGemStone extends AbstractRefinePacket
{
	private int _targetItemObjId;
	private int _mineralItemObjId;
	private int _feeItemObjId;
	private long _feeCount;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_targetItemObjId = packet.readD();
		_mineralItemObjId = packet.readD();
		_feeItemObjId = packet.readD();
		_feeCount = packet.readQ();
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
		
		final ItemInstance targetItem = player.getInventory().getItemByObjectId(_targetItemObjId);
		if (targetItem == null)
		{
			return;
		}
		
		final ItemInstance refinerItem = player.getInventory().getItemByObjectId(_mineralItemObjId);
		if (refinerItem == null)
		{
			return;
		}
		
		final ItemInstance gemStoneItem = player.getInventory().getItemByObjectId(_feeItemObjId);
		if (gemStoneItem == null)
		{
			return;
		}
		
		final VariationFee fee = VariationData.getInstance().getFee(targetItem.getId(), refinerItem.getId());
		if (!isValid(player, targetItem, refinerItem, gemStoneItem, fee))
		{
			client.sendPacket(SystemMessageId.THIS_IS_NOT_A_SUITABLE_ITEM);
			return;
		}
		
		// Check for fee count
		if (_feeCount != fee.getItemCount())
		{
			client.sendPacket(SystemMessageId.GEMSTONE_QUANTITY_IS_INCORRECT);
			return;
		}
		
		client.sendPacket(new ExPutCommissionResultForVariationMake(_feeItemObjId, _feeCount, gemStoneItem.getId()));
	}
}
