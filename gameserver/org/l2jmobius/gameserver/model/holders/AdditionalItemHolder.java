package org.l2jmobius.gameserver.model.holders;

/**
 * @author UnAfraid
 */
public class AdditionalItemHolder extends ItemHolder
{
	private final boolean _allowed;
	
	public AdditionalItemHolder(int id, boolean allowed)
	{
		super(id, 0);
		_allowed = allowed;
	}
	
	public boolean isAllowedToUse()
	{
		return _allowed;
	}
}
