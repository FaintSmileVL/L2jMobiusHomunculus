package org.l2jmobius.gameserver.model.eventengine.drop;

import java.util.Collection;

import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author UnAfraid
 */
public interface IEventDrop
{
	Collection<ItemHolder> calculateDrops();
}
