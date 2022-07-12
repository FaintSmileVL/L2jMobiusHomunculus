package org.l2jmobius.gameserver.model.clientstrings;

/**
 * @author Forsaiken
 */
final class BuilderText extends Builder
{
	private final String _text;
	
	BuilderText(String text)
	{
		_text = text;
	}
	
	@Override
	public String toString(Object param)
	{
		return toString();
	}
	
	@Override
	public String toString(Object... params)
	{
		return toString();
	}
	
	@Override
	public int getIndex()
	{
		return -1;
	}
	
	@Override
	public String toString()
	{
		return _text;
	}
}