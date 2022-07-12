package org.l2jmobius.gameserver.data.xml;

/**
 * @author Mobius
 */
public class ClanLevelData
{
	// TODO: Move to XML.
	private static final int[] CLAN_LEVEL_REQUIREMENTS =
	{
		35000,
		80000,
		140000,
		315000,
		560000,
		965000,
		2690000,
		4050000,
		5930000,
		7560000,
		11830000,
		19110000,
		27300000,
		36400000,
		46410000,
		0
	};
	private static final int[] COMMON_CLAN_MEMBER_LIMIT =
	{
		10,
		15,
		20,
		30,
		40,
		42,
		68,
		85,
		94,
		102,
		111,
		120,
		128,
		137,
		145,
		171
	};
	private static final int[] ELITE_CLAN_MEMBER_LIMIT =
	{
		0,
		0,
		0,
		0,
		0,
		8,
		12,
		15,
		16,
		18,
		19,
		20,
		22,
		23,
		25,
		29
	};
	
	public static int getLevelRequirement(int clanLevel)
	{
		return CLAN_LEVEL_REQUIREMENTS[clanLevel];
	}
	
	public static int getCommonMemberLimit(int clanLevel)
	{
		return COMMON_CLAN_MEMBER_LIMIT[clanLevel];
	}
	
	public static int getEliteMemberLimit(int clanLevel)
	{
		return ELITE_CLAN_MEMBER_LIMIT[clanLevel];
	}
}
