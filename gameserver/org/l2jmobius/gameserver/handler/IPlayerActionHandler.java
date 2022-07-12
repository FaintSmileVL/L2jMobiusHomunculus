package org.l2jmobius.gameserver.handler;

import org.l2jmobius.gameserver.model.ActionDataHolder;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public interface IPlayerActionHandler
{
	void useAction(PlayerInstance player, ActionDataHolder data, boolean ctrlPressed, boolean shiftPressed);
}