package org.l2jmobius.gameserver.handler;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

public interface IUserCommandHandler
{
	Logger LOGGER = Logger.getLogger(IUserCommandHandler.class.getName());
	
	/**
	 * this is the worker method that is called when someone uses an admin command.
	 * @param id
	 * @param player
	 * @return command success
	 */
	boolean useUserCommand(int id, PlayerInstance player);
	
	/**
	 * this method is called at initialization to register all the item ids automatically
	 * @return all known itemIds
	 */
	int[] getUserCommandList();
}
