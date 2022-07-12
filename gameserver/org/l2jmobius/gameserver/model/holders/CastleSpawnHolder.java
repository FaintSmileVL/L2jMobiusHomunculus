package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.enums.CastleSide;
import org.l2jmobius.gameserver.model.Location;

/**
 * @author St3eT
 */
public class CastleSpawnHolder extends Location
{
	private final int _npcId;
	private final CastleSide _side;
	
	public CastleSpawnHolder(int npcId, CastleSide side, int x, int y, int z, int heading)
	{
		super(x, y, z, heading);
		_npcId = npcId;
		_side = side;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public CastleSide getSide()
	{
		return _side;
	}
}
