package org.l2jmobius.gameserver.model.html.pagehandlers;

import org.l2jmobius.gameserver.model.html.IBypassFormatter;
import org.l2jmobius.gameserver.model.html.IHtmlStyle;
import org.l2jmobius.gameserver.model.html.IPageHandler;

/**
 * Creates pager with links 1 2 3 | 9 10 | 998 | 999
 * @author UnAfraid
 */
public class DefaultPageHandler implements IPageHandler
{
	public static final DefaultPageHandler INSTANCE = new DefaultPageHandler(2);
	protected final int _pagesOffset;
	
	public DefaultPageHandler(int pagesOffset)
	{
		_pagesOffset = pagesOffset;
	}
	
	@Override
	public void apply(String bypass, int currentPage, int pages, StringBuilder sb, IBypassFormatter bypassFormatter, IHtmlStyle style)
	{
		final int pagerStart = Math.max(currentPage - _pagesOffset, 0);
		final int pagerFinish = Math.min(currentPage + _pagesOffset + 1, pages);
		
		// Show the initial pages in case we are in the middle or at the end
		if (pagerStart > _pagesOffset)
		{
			for (int i = 0; i < _pagesOffset; i++)
			{
				sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, i), String.valueOf(i + 1), currentPage == i));
			}
			
			// Separator
			sb.append(style.applySeparator());
		}
		
		// Show current pages
		for (int i = pagerStart; i < pagerFinish; i++)
		{
			sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, i), String.valueOf(i + 1), currentPage == i));
		}
		
		// Show the last pages
		if (pages > pagerFinish)
		{
			// Separator
			sb.append(style.applySeparator());
			
			for (int i = pages - _pagesOffset; i < pages; i++)
			{
				sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, i), String.valueOf(i + 1), currentPage == i));
			}
		}
	}
}