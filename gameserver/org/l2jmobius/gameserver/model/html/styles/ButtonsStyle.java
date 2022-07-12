package org.l2jmobius.gameserver.model.html.styles;

import org.l2jmobius.gameserver.model.html.IHtmlStyle;

/**
 * @author UnAfraid
 */
public class ButtonsStyle implements IHtmlStyle
{
	private static final String DEFAULT_PAGE_LINK_FORMAT = "<td><button action=\"%s\" value=\"%s\" width=\"%d\" height=\"%d\" back=\"%s\" fore=\"%s\"></td>";
	private static final String DEFAULT_PAGE_TEXT_FORMAT = "<td>%s</td>";
	private static final String DEFAULT_PAGER_SEPARATOR = "<td align=center> | </td>";
	
	public static final ButtonsStyle INSTANCE = new ButtonsStyle(40, 15, "L2UI_CT1.Button_DF", "L2UI_CT1.Button_DF");
	
	private final int _width;
	private final int _height;
	private final String _back;
	private final String _fore;
	
	public ButtonsStyle(int width, int height, String back, String fore)
	{
		_width = width;
		_height = height;
		_back = back;
		_fore = fore;
	}
	
	@Override
	public String applyBypass(String bypass, String name, boolean isEnabled)
	{
		if (isEnabled)
		{
			return String.format(DEFAULT_PAGE_TEXT_FORMAT, name);
		}
		return String.format(DEFAULT_PAGE_LINK_FORMAT, bypass, name, _width, _height, _back, _fore);
	}
	
	@Override
	public String applySeparator()
	{
		return DEFAULT_PAGER_SEPARATOR;
	}
}
