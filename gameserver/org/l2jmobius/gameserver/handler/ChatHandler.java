package org.l2jmobius.gameserver.handler;

import java.util.EnumMap;
import java.util.Map;

import org.l2jmobius.gameserver.enums.ChatType;

/**
 * This class handles all chat handlers
 * @author durgus, UnAfraid
 */
public class ChatHandler implements IHandler<IChatHandler, ChatType>
{
	private final Map<ChatType, IChatHandler> _datatable = new EnumMap<>(ChatType.class);
	
	/**
	 * Singleton constructor
	 */
	protected ChatHandler()
	{
	}
	
	/**
	 * Register a new chat handler
	 * @param handler
	 */
	@Override
	public void registerHandler(IChatHandler handler)
	{
		for (ChatType type : handler.getChatTypeList())
		{
			_datatable.put(type, handler);
		}
	}
	
	@Override
	public synchronized void removeHandler(IChatHandler handler)
	{
		for (ChatType type : handler.getChatTypeList())
		{
			_datatable.remove(type);
		}
	}
	
	/**
	 * Get the chat handler for the given chat type
	 * @param chatType
	 * @return
	 */
	@Override
	public IChatHandler getHandler(ChatType chatType)
	{
		return _datatable.get(chatType);
	}
	
	/**
	 * Returns the size
	 * @return
	 */
	@Override
	public int size()
	{
		return _datatable.size();
	}
	
	public static ChatHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ChatHandler INSTANCE = new ChatHandler();
	}
}