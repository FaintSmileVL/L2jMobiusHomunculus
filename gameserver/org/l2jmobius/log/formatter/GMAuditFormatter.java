package org.l2jmobius.log.formatter;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.l2jmobius.Config;

public class GMAuditFormatter extends Formatter
{
	@Override
	public String format(LogRecord record)
	{
		return record.getMessage() + Config.EOL;
	}
}