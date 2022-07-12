package org.l2jmobius.gameserver.handler;

import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Interface for chat handlers
 * @author durgus
 */
public interface IChatHandler
{
	/**
	 * Handles a specific type of chat messages
	 * @param type
	 * @param player
	 * @param target
	 * @param text
	 */
	void handleChat(ChatType type, PlayerInstance player, String target, String text);
	
	/**
	 * Returns a list of all chat types registered to this handler
	 * @return
	 */
	ChatType[] getChatTypeList();
}