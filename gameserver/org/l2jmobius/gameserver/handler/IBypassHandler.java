package org.l2jmobius.gameserver.handler;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author nBd
 */
public interface IBypassHandler
{
	Logger LOGGER = Logger.getLogger(IBypassHandler.class.getName());
	
	/**
	 * This is the worker method that is called when someone uses an bypass command.
	 * @param command
	 * @param player
	 * @param bypassOrigin
	 * @return success
	 */
	boolean useBypass(String command, PlayerInstance player, Creature bypassOrigin);
	
	/**
	 * This method is called at initialization to register all bypasses automatically.
	 * @return all known bypasses
	 */
	String[] getBypassList();
}