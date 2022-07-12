package org.l2jmobius.log.filter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ChatFilter implements Filter
{
	@Override
	public boolean isLoggable(LogRecord record)
	{
		return "chat".equals(record.getLoggerName());
	}
}
