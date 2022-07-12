package org.l2jmobius.log.filter;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.items.type.EtcItemType;
import org.l2jmobius.gameserver.model.items.type.ItemType;

/**
 * @author Advi
 */
public class ItemFilter implements Filter
{
	// private String _excludeProcess;
	// private String _excludeItemType;
	
	// This is an example how to exclude consuming of shots and arrows from logging
	private static final String EXCLUDE_PROCESS = "Consume";
	private static final Set<ItemType> EXCLUDED_ITEM_TYPES = new HashSet<>();
	static
	{
		EXCLUDED_ITEM_TYPES.add(EtcItemType.ARROW);
		EXCLUDED_ITEM_TYPES.add(EtcItemType.SOULSHOT);
	}
	
	@Override
	public boolean isLoggable(LogRecord record)
	{
		if (!"item".equals(record.getLoggerName()))
		{
			return false;
		}
		
		final String[] messageList = record.getMessage().split(":");
		return (messageList.length < 2) || !EXCLUDE_PROCESS.contains(messageList[1]) || !EXCLUDED_ITEM_TYPES.contains(((ItemInstance) record.getParameters()[0]).getItemType());
	}
}
