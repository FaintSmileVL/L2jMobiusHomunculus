package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.interfaces.IUniqueId;

/**
 * A DTO for items; contains item ID, object ID and count.
 * @author xban1x
 */
public class UniqueItemHolder extends ItemHolder implements IUniqueId
{
	private final int _objectId;
	
	public UniqueItemHolder(int id, int objectId)
	{
		this(id, objectId, 1);
	}
	
	public UniqueItemHolder(int id, int objectId, long count)
	{
		super(id, count);
		_objectId = objectId;
	}
	
	@Override
	public int getObjectId()
	{
		return _objectId;
	}
	
	@Override
	public String toString()
	{
		return "[" + getClass().getSimpleName() + "] ID: " + getId() + ", object ID: " + _objectId + ", count: " + getCount();
	}
}
