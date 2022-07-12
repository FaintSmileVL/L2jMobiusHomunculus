package org.l2jmobius.gameserver.model.html.pagehandlers;

import org.l2jmobius.gameserver.model.html.IBypassFormatter;
import org.l2jmobius.gameserver.model.html.IHtmlStyle;
import org.l2jmobius.gameserver.model.html.IPageHandler;

/**
 * Creates pager with links << | < | > | >>
 * @author UnAfraid
 */
public class NextPrevPageHandler implements IPageHandler
{
	public static final NextPrevPageHandler INSTANCE = new NextPrevPageHandler();
	
	@Override
	public void apply(String bypass, int currentPage, int pages, StringBuilder sb, IBypassFormatter bypassFormatter, IHtmlStyle style)
	{
		// Beginning
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, 0), "<<", (currentPage - 1) < 0));
		
		// Separator
		sb.append(style.applySeparator());
		
		// Previous
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, currentPage - 1), "<", currentPage <= 0));
		sb.append(style.applySeparator());
		sb.append(String.format("<td align=\"center\">Page: %d/%d</td>", currentPage + 1, pages + 1));
		sb.append(style.applySeparator());
		
		// Next
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, currentPage + 1), ">", currentPage >= pages));
		
		// Separator
		sb.append(style.applySeparator());
		
		// End
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, pages), ">>", (currentPage + 1) > pages));
	}
}