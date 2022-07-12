package org.l2jmobius.gameserver.model.events.returns;

import org.l2jmobius.gameserver.enums.ChatType;

/**
 * @author UnAfraid
 */
public class ChatFilterReturn extends AbstractEventReturn
{
	private final String _filteredText;
	private final ChatType _chatType;
	
	public ChatFilterReturn(String filteredText, ChatType newChatType, boolean override, boolean abort)
	{
		super(override, abort);
		_filteredText = filteredText;
		_chatType = newChatType;
	}
	
	public String getFilteredText()
	{
		return _filteredText;
	}
	
	public ChatType getChatType()
	{
		return _chatType;
	}
}
