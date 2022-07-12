package org.l2jmobius.gameserver.network.clientpackets.autoplay;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.ActionData;
import org.l2jmobius.gameserver.handler.IPlayerActionHandler;
import org.l2jmobius.gameserver.handler.PlayerActionHandler;
import org.l2jmobius.gameserver.model.ActionDataHolder;
import org.l2jmobius.gameserver.model.Shortcut;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.autoplay.ExActivateAutoShortcut;
import org.l2jmobius.gameserver.taskmanager.AutoUseTaskManager;

/**
 * @author JoeAlisson, Mobius
 */
public class ExRequestActivateAutoShortcut implements IClientIncomingPacket
{
	private boolean _activate;
	private int _room;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_room = packet.readH();
		_activate = packet.readC() == 1;
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
		
		final int slot = _room % 12;
		final int page = _room / 12;
		final Shortcut shortcut = player.getShortCut(slot, page);
		if (shortcut == null)
		{
			return;
		}
		client.sendPacket(new ExActivateAutoShortcut(_room, _activate));
		
		final ItemInstance item = player.getInventory().getItemByObjectId(shortcut.getId());
		Skill skill = null;
		if (item == null)
		{
			skill = player.getKnownSkill(shortcut.getId());
		}
		
		// stop
		if (!_activate)
		{
			if (item != null)
			{
				// auto supply
				if (!item.isPotion())
				{
					AutoUseTaskManager.getInstance().removeAutoSupplyItem(player, item.getId());
				}
				else // auto potion
				{
					AutoUseTaskManager.getInstance().removeAutoPotionItem(player, item.getId());
				}
			}
			// auto skill
			if (skill != null)
			{
				AutoUseTaskManager.getInstance().removeAutoSkill(player, skill.getId());
			}
			else // action
			{
				AutoUseTaskManager.getInstance().removeAutoAction(player, shortcut.getId());
			}
			return;
		}
		
		// start
		if ((item != null) && !item.isPotion())
		{
			// auto supply
			if (Config.ENABLE_AUTO_ITEM)
			{
				AutoUseTaskManager.getInstance().addAutoSupplyItem(player, item.getId());
			}
		}
		else
		{
			// auto potion
			if ((page == 23) && (slot == 1))
			{
				if (Config.ENABLE_AUTO_POTION && (item != null) && item.isPotion())
				{
					AutoUseTaskManager.getInstance().addAutoPotionItem(player, item.getId());
					return;
				}
			}
			// auto skill
			if (Config.ENABLE_AUTO_BUFF && (skill != null))
			{
				AutoUseTaskManager.getInstance().addAutoSkill(player, skill.getId());
				return;
			}
			// action
			final ActionDataHolder actionHolder = ActionData.getInstance().getActionData(shortcut.getId());
			if (actionHolder != null)
			{
				final IPlayerActionHandler actionHandler = PlayerActionHandler.getInstance().getHandler(actionHolder.getHandler());
				if (actionHandler != null)
				{
					AutoUseTaskManager.getInstance().addAutoAction(player, shortcut.getId());
				}
			}
		}
	}
}
