package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.network.NpcStringId;

/**
 * @author St3eT
 */
public class ClanHallTeleportHolder extends Location
{
	private final NpcStringId _npcStringId;
	private final int _minFunctionLevel;
	private final int _cost;
	
	public ClanHallTeleportHolder(int npcStringId, int x, int y, int z, int minFunctionLevel, int cost)
	{
		super(x, y, z);
		_npcStringId = NpcStringId.getNpcStringId(npcStringId);
		_minFunctionLevel = minFunctionLevel;
		_cost = cost;
	}
	
	public NpcStringId getNpcStringId()
	{
		return _npcStringId;
	}
	
	public int getMinFunctionLevel()
	{
		return _minFunctionLevel;
	}
	
	public int getCost()
	{
		return _cost;
	}
}
