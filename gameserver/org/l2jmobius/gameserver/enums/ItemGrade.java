package org.l2jmobius.gameserver.enums;

import org.l2jmobius.gameserver.model.items.type.CrystalType;

/**
 * @author UnAfraid
 */
public enum ItemGrade
{
	NONE,
	D,
	C,
	B,
	A,
	S,
	R;
	
	public static ItemGrade valueOf(CrystalType type)
	{
		switch (type)
		{
			case NONE:
			{
				return NONE;
			}
			case D:
			{
				return D;
			}
			case C:
			{
				return C;
			}
			case B:
			{
				return B;
			}
			case A:
			{
				return A;
			}
			case S:
			case S80:
			case S84:
			{
				return S;
			}
			case R:
			case R95:
			case R99:
			case R110:
			{
				return R;
			}
		}
		return null;
	}
}
