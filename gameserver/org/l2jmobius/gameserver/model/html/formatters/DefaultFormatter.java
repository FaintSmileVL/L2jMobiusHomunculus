package org.l2jmobius.gameserver.model.html.formatters;

import org.l2jmobius.gameserver.model.html.IBypassFormatter;

/**
 * @author UnAfraid
 */
public class DefaultFormatter implements IBypassFormatter
{
	public static final DefaultFormatter INSTANCE = new DefaultFormatter();
	
	@Override
	public String formatBypass(String bypass, int page)
	{
		return bypass + " " + page;
	}
}
