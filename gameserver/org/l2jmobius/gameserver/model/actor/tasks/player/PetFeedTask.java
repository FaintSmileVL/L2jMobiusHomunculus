package org.l2jmobius.gameserver.model.actor.tasks.player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.handler.IItemHandler;
import org.l2jmobius.gameserver.handler.ItemHandler;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * Task dedicated for feeding player's pet.
 * @author UnAfraid
 */
public class PetFeedTask implements Runnable
{
	private static final Logger LOGGER = Logger.getLogger(PetFeedTask.class.getName());
	
	private final PlayerInstance _player;
	
	public PetFeedTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			try
			{
				if (!_player.isMounted() || (_player.getMountNpcId() == 0) || (_player.getPetData(_player.getMountNpcId()) == null))
				{
					_player.stopFeed();
					return;
				}
				
				if (_player.getCurrentFeed() > _player.getFeedConsume())
				{
					// eat
					_player.setCurrentFeed(_player.getCurrentFeed() - _player.getFeedConsume());
				}
				else
				{
					// go back to pet control item, or simply said, unsummon it
					_player.setCurrentFeed(0);
					_player.stopFeed();
					_player.dismount();
					_player.sendPacket(SystemMessageId.YOU_ARE_OUT_OF_FEED_MOUNT_STATUS_CANCELED);
				}
				
				final List<Integer> foodIds = _player.getPetData(_player.getMountNpcId()).getFood();
				if (foodIds.isEmpty())
				{
					return;
				}
				ItemInstance food = null;
				for (int id : foodIds)
				{
					// TODO: possibly pet inv?
					food = _player.getInventory().getItemByItemId(id);
					if (food != null)
					{
						break;
					}
				}
				
				if ((food != null) && _player.isHungry())
				{
					final IItemHandler handler = ItemHandler.getInstance().getHandler(food.getEtcItem());
					if (handler != null)
					{
						handler.useItem(_player, food, false);
						final SystemMessage sm = new SystemMessage(SystemMessageId.YOUR_PET_WAS_HUNGRY_SO_IT_ATE_S1);
						sm.addItemName(food.getId());
						_player.sendPacket(sm);
					}
				}
			}
			catch (Exception e)
			{
				LOGGER.log(Level.SEVERE, "Mounted Pet [NpcId: " + _player.getMountNpcId() + "] a feed task error has occurred", e);
			}
		}
	}
}
