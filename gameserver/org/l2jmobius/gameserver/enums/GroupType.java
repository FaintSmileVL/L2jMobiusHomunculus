package org.l2jmobius.gameserver.enums;

import org.l2jmobius.gameserver.model.interfaces.IUpdateTypeComponent;

/**
 * @author malyelfik
 */
public enum GroupType implements IUpdateTypeComponent
{
	NONE(0x01),
	PARTY(0x02),
	COMMAND_CHANNEL(0x04);
	
	private int _mask;
	
	private GroupType(int mask)
	{
		_mask = mask;
	}
	
	@Override
	public int getMask()
	{
		return _mask;
	}
	
	public static GroupType getByMask(int flag)
	{
		for (GroupType type : values())
		{
			if (type.getMask() == flag)
			{
				return type;
			}
		}
		return null;
	}
}