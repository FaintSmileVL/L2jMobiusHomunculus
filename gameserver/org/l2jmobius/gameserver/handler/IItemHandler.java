package org.l2jmobius.gameserver.handler;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.actor.Playable;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * Mother class of all Item Handlers.
 */
public interface IItemHandler
{
	Logger LOGGER = Logger.getLogger(IItemHandler.class.getName());
	
	/**
	 * Launch task associated to the item.
	 * @param playable the non-NPC character using the item
	 * @param item ItemInstance designating the item to use
	 * @param forceUse ctrl hold on item use
	 * @return {@code true} if the item all conditions are met and the item is used, {@code false} otherwise.
	 */
	boolean useItem(Playable playable, ItemInstance item, boolean forceUse);
}
