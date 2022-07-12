package org.l2jmobius.gameserver.model.olympiad;

/**
 * @author DS
 */
public enum CompetitionType
{
	CLASSED("classed"),
	NON_CLASSED("non-classed"),
	OTHER("other");
	
	private final String _name;
	
	CompetitionType(String name)
	{
		_name = name;
	}
	
	@Override
	public String toString()
	{
		return _name;
	}
}