package org.l2jmobius.log.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.l2jmobius.Config;
import util.CommonUtil;
import util.StringUtil;

public class ConsoleLogFormatter extends Formatter
{
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM HH:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		final StringBuilder output = new StringBuilder(500);
		StringUtil.append(output, "[", dateFmt.format(new Date(record.getMillis())), "] " + record.getMessage(), Config.EOL);
		
		if (record.getThrown() != null)
		{
			try
			{
				StringUtil.append(output, CommonUtil.getStackTrace(record.getThrown()), Config.EOL);
			}
			catch (Exception ex)
			{
				// Ignore.
			}
		}
		return output.toString();
	}
}
