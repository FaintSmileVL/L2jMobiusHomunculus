package org.l2jmobius.gameserver.model.html;

/**
 * @author UnAfraid
 */
public class PageResult
{
	private final int _pages;
	private final StringBuilder _pagerTemplate;
	private final StringBuilder _bodyTemplate;
	
	public PageResult(int pages, StringBuilder pagerTemplate, StringBuilder bodyTemplate)
	{
		_pages = pages;
		_pagerTemplate = pagerTemplate;
		_bodyTemplate = bodyTemplate;
	}
	
	public int getPages()
	{
		return _pages;
	}
	
	public StringBuilder getPagerTemplate()
	{
		return _pagerTemplate;
	}
	
	public StringBuilder getBodyTemplate()
	{
		return _bodyTemplate;
	}
}