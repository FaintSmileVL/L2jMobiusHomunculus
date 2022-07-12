package org.l2jmobius.log.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.l2jmobius.Config;
import util.StringUtil;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author Advi
 */
public class ItemLogFormatter extends Formatter
{
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMM H:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		final Object[] params = record.getParameters();
		final StringBuilder output = StringUtil.startAppend(30 + record.getMessage().length() + (params != null ? params.length * 50 : 0), "[", dateFmt.format(new Date(record.getMillis())), "] ", record.getMessage());
		
		if (params != null)
		{
			for (Object p : params)
			{
				if (p == null)
				{
					continue;
				}
				output.append(", ");
				if (p instanceof ItemInstance)
				{
					final ItemInstance item = (ItemInstance) p;
					StringUtil.append(output, "item ", String.valueOf(item.getObjectId()), ":");
					if (item.getEnchantLevel() > 0)
					{
						StringUtil.append(output, "+", String.valueOf(item.getEnchantLevel()), " ");
					}
					
					StringUtil.append(output, item.getItem().getName(), "(", String.valueOf(item.getCount()), ")");
				}
				else
				{
					output.append(p.toString());
				}
			}
		}
		output.append(Config.EOL);
		
		return output.toString();
	}
}
