package org.l2jmobius.gameserver.handler;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

public interface IVoicedCommandHandler
{
	/**
	 * this is the worker method that is called when someone uses an admin command.
	 * @param player
	 * @param command
	 * @param params
	 * @return command success
	 */
	boolean useVoicedCommand(String command, PlayerInstance player, String params);
	
	/**
	 * this method is called at initialization to register all the item ids automatically
	 * @return all known itemIds
	 */
	String[] getVoicedCommandList();
}
