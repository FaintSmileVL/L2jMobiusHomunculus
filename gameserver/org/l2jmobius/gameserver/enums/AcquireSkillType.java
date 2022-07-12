package org.l2jmobius.gameserver.enums;

/**
 * Learning skill types.
 * @author Zoey76
 */
public enum AcquireSkillType
{
	CLASS(0),
	DUMMY(1),
	PLEDGE(2),
	SUBPLEDGE(3),
	TRANSFORM(4),
	TRANSFER(5),
	SUBCLASS(6),
	COLLECT(7),
	DUMMY2(8),
	DUMMY3(9),
	FISHING(10),
	REVELATION(11), // Need proper ID
	REVELATION_DUALCLASS(12), // Need proper ID
	DUALCLASS(13), // Need proper ID
	ALCHEMY(140);
	
	private final int _id;
	
	private AcquireSkillType(int id)
	{
		_id = id;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public static AcquireSkillType getAcquireSkillType(int id)
	{
		for (AcquireSkillType type : values())
		{
			if (type.getId() == id)
			{
				return type;
			}
		}
		return null;
	}
}
