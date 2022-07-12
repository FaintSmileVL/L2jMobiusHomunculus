package org.l2jmobius.gameserver.handler;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Community Board interface.
 * @author Zoey76
 */
public interface IParseBoardHandler
{
	Logger LOG = Logger.getLogger(IParseBoardHandler.class.getName());
	
	/**
	 * Parses a community board command.
	 * @param command the command
	 * @param player the player
	 * @return
	 */
	boolean parseCommunityBoardCommand(String command, PlayerInstance player);
	
	/**
	 * Gets the community board commands.
	 * @return the community board commands
	 */
	String[] getCommunityBoardCommands();
}
