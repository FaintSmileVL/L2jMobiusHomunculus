package org.l2jmobius.gameserver.model;

/**
 * @author UnAfraid
 */
public class ActionDataHolder
{
	private final int _id;
	private final String _handler;
	private final int _optionId;
	
	public ActionDataHolder(StatSet set)
	{
		_id = set.getInt("id");
		_handler = set.getString("handler");
		_optionId = set.getInt("option", 0);
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getHandler()
	{
		return _handler;
	}
	
	public int getOptionId()
	{
		return _optionId;
	}
}
