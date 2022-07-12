package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.enums.SiegeGuardType;

/**
 * @author St3eT
 */
public class SiegeGuardHolder
{
	private final int _castleId;
	private final int _itemId;
	private final SiegeGuardType _type;
	private final boolean _stationary;
	private final int _npcId;
	private final int _maxNpcAmount;
	
	public SiegeGuardHolder(int castleId, int itemId, SiegeGuardType type, boolean stationary, int npcId, int maxNpcAmount)
	{
		_castleId = castleId;
		_itemId = itemId;
		_type = type;
		_stationary = stationary;
		_npcId = npcId;
		_maxNpcAmount = maxNpcAmount;
	}
	
	public int getCastleId()
	{
		return _castleId;
	}
	
	public int getItemId()
	{
		return _itemId;
	}
	
	public SiegeGuardType getType()
	{
		return _type;
	}
	
	public boolean isStationary()
	{
		return _stationary;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public int getMaxNpcAmout()
	{
		return _maxNpcAmount;
	}
}
