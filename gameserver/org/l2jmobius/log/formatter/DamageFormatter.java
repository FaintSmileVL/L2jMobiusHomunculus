package org.l2jmobius.log.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.l2jmobius.Config;
import util.StringUtil;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

public class DamageFormatter extends Formatter
{
	private final SimpleDateFormat dateFmt = new SimpleDateFormat("yy.MM.dd H:mm:ss");
	
	@Override
	public String format(LogRecord record)
	{
		final Object[] params = record.getParameters();
		final StringBuilder output = StringUtil.startAppend(30 + record.getMessage().length() + (params == null ? 0 : params.length * 10), "[", dateFmt.format(new Date(record.getMillis())), "] '---': ", record.getMessage());
		
		if (params != null)
		{
			for (Object p : params)
			{
				if (p == null)
				{
					continue;
				}
				
				if (p instanceof Creature)
				{
					if (((Creature) p).isRaid())
					{
						StringUtil.append(output, "RaidBoss ");
					}
					
					StringUtil.append(output, ((Creature) p).getName(), "(", String.valueOf(((Creature) p).getObjectId()), ") ");
					StringUtil.append(output, String.valueOf(((Creature) p).getLevel()), " lvl");
					
					if (p instanceof Summon)
					{
						final PlayerInstance owner = ((Summon) p).getOwner();
						if (owner != null)
						{
							StringUtil.append(output, " Owner:", owner.getName(), "(", String.valueOf(owner.getObjectId()), ")");
						}
					}
				}
				else if (p instanceof Skill)
				{
					StringUtil.append(output, " with skill ", ((Skill) p).getName(), "(", String.valueOf(((Skill) p).getId()), ")");
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
