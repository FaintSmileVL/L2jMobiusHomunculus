package org.l2jmobius.gameserver.model.zone.type;

import util.Chronos;
import org.l2jmobius.gameserver.GameServer;
import org.l2jmobius.gameserver.enums.TeleportWhereType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * A simple no restart zone
 * @author GKR
 */
public class NoRestartZone extends ZoneType
{
	private int _restartAllowedTime = 0;
	private int _restartTime = 0;
	private boolean _enabled = true;
	
	public NoRestartZone(int id)
	{
		super(id);
	}
	
	@Override
	public void setParameter(String name, String value)
	{
		if (name.equalsIgnoreCase("default_enabled"))
		{
			_enabled = Boolean.parseBoolean(value);
		}
		else if (name.equalsIgnoreCase("restartAllowedTime"))
		{
			_restartAllowedTime = Integer.parseInt(value) * 1000;
		}
		else if (name.equalsIgnoreCase("restartTime"))
		{
			_restartTime = Integer.parseInt(value) * 1000;
		}
		else if (name.equalsIgnoreCase("instanceId"))
		{
			// Do nothing.
		}
		else
		{
			super.setParameter(name, value);
		}
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		if (!_enabled)
		{
			return;
		}
		
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.NO_RESTART, true);
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (!_enabled)
		{
			return;
		}
		
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.NO_RESTART, false);
		}
	}
	
	@Override
	public void onPlayerLoginInside(PlayerInstance player)
	{
		if (!_enabled)
		{
			return;
		}
		
		if (((Chronos.currentTimeMillis() - player.getLastAccess()) > _restartTime) && ((Chronos.currentTimeMillis() - GameServer.dateTimeServerStarted.getTimeInMillis()) > _restartAllowedTime))
		{
			player.teleToLocation(TeleportWhereType.TOWN);
		}
	}
	
	public int getRestartAllowedTime()
	{
		return _restartAllowedTime;
	}
	
	public void setRestartAllowedTime(int time)
	{
		_restartAllowedTime = time;
	}
	
	public int getRestartTime()
	{
		return _restartTime;
	}
	
	public void setRestartTime(int time)
	{
		_restartTime = time;
	}
}
