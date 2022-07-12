package org.l2jmobius.gameserver.network.clientpackets.crystalization;

import java.util.List;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.ItemCrystallizationData;
import org.l2jmobius.gameserver.enums.PrivateStoreType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ItemChanceHolder;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.items.type.CrystalType;
import org.l2jmobius.gameserver.model.skills.CommonSkill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.crystalization.ExGetCrystalizingEstimation;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class RequestCrystallizeEstimate implements IClientIncomingPacket
{
	private int _objectId;
	private long _count;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		_count = packet.readQ();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || player.isInCrystallize())
		{
			return;
		}
		
		// if (!client.getFloodProtectors().getTransaction().tryPerformAction("crystallize"))
		// {
		// player.sendMessage("You are crystallizing too fast.");
		// return;
		// }
		if (_count <= 0)
		{
			Util.handleIllegalPlayerAction(player, "[RequestCrystallizeItem] count <= 0! ban! oid: " + _objectId + " owner: " + player.getName(), Config.DEFAULT_PUNISH);
			return;
		}
		
		if ((player.getPrivateStoreType() != PrivateStoreType.NONE) || player.isInCrystallize())
		{
			client.sendPacket(SystemMessageId.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
			return;
		}
		
		final int skillLevel = player.getSkillLevel(CommonSkill.CRYSTALLIZE.getId());
		if (skillLevel <= 0)
		{
			client.sendPacket(SystemMessageId.YOU_MAY_NOT_CRYSTALLIZE_THIS_ITEM_YOUR_CRYSTALLIZATION_SKILL_LEVEL_IS_TOO_LOW);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final ItemInstance item = player.getInventory().getItemByObjectId(_objectId);
		if ((item == null) || item.isShadowItem() || item.isTimeLimitedItem() || item.isHeroItem() || (!Config.ALT_ALLOW_AUGMENT_DESTROY && item.isAugmented()))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!item.getItem().isCrystallizable() || (item.getItem().getCrystalCount() <= 0) || (item.getItem().getCrystalType() == CrystalType.NONE))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			LOGGER.warning(player + ": tried to crystallize " + item.getItem());
			return;
		}
		
		if (_count > item.getCount())
		{
			_count = player.getInventory().getItemByObjectId(_objectId).getCount();
		}
		
		if (!player.getInventory().canManipulateWithItemId(item.getId()))
		{
			player.sendMessage("You cannot use this item.");
			return;
		}
		
		// Check if the char can crystallize items and return if false;
		boolean canCrystallize = true;
		
		switch (item.getItem().getCrystalTypePlus())
		{
			case D:
			{
				if (skillLevel < 1)
				{
					canCrystallize = false;
				}
				break;
			}
			case C:
			{
				if (skillLevel < 2)
				{
					canCrystallize = false;
				}
				break;
			}
			case B:
			{
				if (skillLevel < 3)
				{
					canCrystallize = false;
				}
				break;
			}
			case A:
			{
				if (skillLevel < 4)
				{
					canCrystallize = false;
				}
				break;
			}
			case S:
			{
				if (skillLevel < 5)
				{
					canCrystallize = false;
				}
				break;
			}
			case R:
			{
				if (skillLevel < 6)
				{
					canCrystallize = false;
				}
				break;
			}
		}
		
		if (!canCrystallize)
		{
			client.sendPacket(SystemMessageId.YOU_MAY_NOT_CRYSTALLIZE_THIS_ITEM_YOUR_CRYSTALLIZATION_SKILL_LEVEL_IS_TOO_LOW);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		// Show crystallization rewards window.
		final List<ItemChanceHolder> crystallizationRewards = ItemCrystallizationData.getInstance().getCrystallizationRewards(item);
		if ((crystallizationRewards != null) && !crystallizationRewards.isEmpty())
		{
			player.setInCrystallize(true);
			client.sendPacket(new ExGetCrystalizingEstimation(crystallizationRewards));
		}
		else
		{
			player.sendMessage("Crystallization cannot be proceeded because there are no items registered.");
			// CRYSTALLIZATION_CANNOT_BE_PROCEEDED_BECAUSE_THERE_ARE_NO_ITEMS_REGISTERED changed to ANGEL_NEVIT_S_DESCENT_BONUS_TIME_S1
			// client.sendPacket(SystemMessageId.CRYSTALLIZATION_CANNOT_BE_PROCEEDED_BECAUSE_THERE_ARE_NO_ITEMS_REGISTERED);
		}
	}
}
