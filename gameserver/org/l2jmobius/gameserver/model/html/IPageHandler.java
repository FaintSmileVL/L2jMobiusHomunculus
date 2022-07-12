package org.l2jmobius.gameserver.model.html;

/**
 * @author UnAfraid
 */
@FunctionalInterface
public interface IPageHandler
{
	void apply(String bypass, int currentPage, int pages, StringBuilder sb, IBypassFormatter bypassFormatter, IHtmlStyle style);
}