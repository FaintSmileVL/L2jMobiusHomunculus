package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneRespawn;

/**
 * @author xban1x
 */
public abstract class ResidenceZone extends ZoneRespawn
{
	private int _residenceId;
	
	protected ResidenceZone(int id)
	{
		super(id);
	}
	
	public void banishForeigners(int owningClanId)
	{
		for (PlayerInstance temp : getPlayersInside())
		{
			if ((owningClanId != 0) && (temp.getClanId() == owningClanId))
			{
				continue;
			}
			temp.teleToLocation(getBanishSpawnLoc(), true);
		}
	}
	
	protected void setResidenceId(int residenceId)
	{
		_residenceId = residenceId;
	}
	
	public int getResidenceId()
	{
		return _residenceId;
	}
}
