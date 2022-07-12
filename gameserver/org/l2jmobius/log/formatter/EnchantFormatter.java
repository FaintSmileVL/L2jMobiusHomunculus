package org.l2jmobius.log.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.l2jmobius.Config;
import util.StringUtil;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

public class EnchantFormatter extends Formatter
{
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMM H:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		final Object[] params = record.getParameters();
		final StringBuilder output = StringUtil.startAppend(30 + record.getMessage().length() + (params == null ? 0 : params.length * 10), "[", dateFmt.format(new Date(record.getMillis())), "] ", record.getMessage());
		
		if (params != null)
		{
			for (Object p : params)
			{
				if (p == null)
				{
					continue;
				}
				
				StringUtil.append(output, ", ");
				
				if (p instanceof PlayerInstance)
				{
					final PlayerInstance player = (PlayerInstance) p;
					StringUtil.append(output, "Character:", player.getName(), " [" + player.getObjectId() + "] Account:", player.getAccountName());
					if ((player.getClient() != null) && !player.getClient().isDetached())
					{
						StringUtil.append(output, " IP:", player.getClient().getConnectionAddress().getHostAddress());
					}
				}
				else if (p instanceof ItemInstance)
				{
					final ItemInstance item = (ItemInstance) p;
					if (item.getEnchantLevel() > 0)
					{
						StringUtil.append(output, "+", String.valueOf(item.getEnchantLevel()), " ");
					}
					StringUtil.append(output, item.getItem().getName(), "(", String.valueOf(item.getCount()), ")");
					StringUtil.append(output, " [", String.valueOf(item.getObjectId()), "]");
				}
				else if (p instanceof Skill)
				{
					final Skill skill = (Skill) p;
					if (skill.getLevel() > 100)
					{
						StringUtil.append(output, "+", String.valueOf(skill.getLevel() % 100), " ");
					}
					StringUtil.append(output, skill.getName(), "(", String.valueOf(skill.getId()), " ", String.valueOf(skill.getLevel()), ")");
				}
				else
				{
					StringUtil.append(output, p.toString());
				}
			}
		}
		
		output.append(Config.EOL);
		return output.toString();
	}
}
