package org.l2jmobius.gameserver.model.ceremonyofchaos;

import org.l2jmobius.gameserver.enums.CeremonyOfChaosResult;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.eventengine.AbstractEventMember;

/**
 * @author UnAfraid
 */
public class CeremonyOfChaosMember extends AbstractEventMember<CeremonyOfChaosEvent>
{
	private final int _position;
	private int _lifeTime = 0;
	private CeremonyOfChaosResult _resultType = CeremonyOfChaosResult.LOSE;
	private boolean _isDefeated = false;
	
	public CeremonyOfChaosMember(PlayerInstance player, CeremonyOfChaosEvent event, int position)
	{
		super(player, event);
		_position = position;
	}
	
	public int getPosition()
	{
		return _position;
	}
	
	public void setLifeTime(int time)
	{
		_lifeTime = time;
	}
	
	public int getLifeTime()
	{
		return _lifeTime;
	}
	
	public CeremonyOfChaosResult getResultType()
	{
		return _resultType;
	}
	
	public void setResultType(CeremonyOfChaosResult resultType)
	{
		_resultType = resultType;
	}
	
	public boolean isDefeated()
	{
		return _isDefeated;
	}
	
	public void setDefeated(boolean isDefeated)
	{
		_isDefeated = isDefeated;
	}
}
