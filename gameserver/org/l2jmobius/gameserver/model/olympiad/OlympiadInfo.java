package org.l2jmobius.gameserver.model.olympiad;

/**
 * @author JIV
 */
public class OlympiadInfo
{
	private final String _name;
	private final String _clan;
	private final int _clanId;
	private final int _classId;
	private final int _dmg;
	private final int _curPoints;
	private final int _diffPoints;
	
	public OlympiadInfo(String name, String clan, int clanId, int classId, int dmg, int curPoints, int diffPoints)
	{
		_name = name;
		_clan = clan;
		_clanId = clanId;
		_classId = classId;
		_dmg = dmg;
		_curPoints = curPoints;
		_diffPoints = diffPoints;
	}
	
	/**
	 * @return the name the player's name.
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * @return the name the player's clan name.
	 */
	public String getClanName()
	{
		return _clan;
	}
	
	/**
	 * @return the name the player's clan id.
	 */
	public int getClanId()
	{
		return _clanId;
	}
	
	/**
	 * @return the name the player's class id.
	 */
	public int getClassId()
	{
		return _classId;
	}
	
	/**
	 * @return the name the player's damage.
	 */
	public int getDamage()
	{
		return _dmg;
	}
	
	/**
	 * @return the name the player's current points.
	 */
	public int getCurrentPoints()
	{
		return _curPoints;
	}
	
	/**
	 * @return the name the player's points difference since this match.
	 */
	public int getDiffPoints()
	{
		return _diffPoints;
	}
}