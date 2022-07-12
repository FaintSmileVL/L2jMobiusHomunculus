package org.l2jmobius.gameserver.model;

import org.l2jmobius.gameserver.model.interfaces.IIdentifiable;

/**
 * Fort Siege Spawn.
 * @author xban1x
 */
public class FortSiegeSpawn extends Location implements IIdentifiable
{
	private final int _npcId;
	private final int _fortId;
	private final int _id;
	
	public FortSiegeSpawn(int fortId, int x, int y, int z, int heading, int npcId, int id)
	{
		super(x, y, z, heading);
		_fortId = fortId;
		_npcId = npcId;
		_id = id;
	}
	
	public int getFortId()
	{
		return _fortId;
	}
	
	/**
	 * Gets the NPC ID.
	 * @return the NPC ID
	 */
	@Override
	public int getId()
	{
		return _npcId;
	}
	
	public int getMessageId()
	{
		return _id;
	}
}
