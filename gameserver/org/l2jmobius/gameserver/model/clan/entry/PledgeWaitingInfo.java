package org.l2jmobius.gameserver.model.clan.entry;

import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Sdw
 */
public class PledgeWaitingInfo
{
	private int _playerId;
	private int _playerClassId;
	private int _playerLvl;
	private final int _karma;
	private String _playerName;
	
	public PledgeWaitingInfo(int playerId, int playerLvl, int karma, int classId, String playerName)
	{
		_playerId = playerId;
		_playerClassId = classId;
		_playerLvl = playerLvl;
		_karma = karma;
		_playerName = playerName;
	}
	
	public int getPlayerId()
	{
		return _playerId;
	}
	
	public void setPlayerId(int playerId)
	{
		_playerId = playerId;
	}
	
	public int getPlayerClassId()
	{
		if (isOnline() && (getPlayerInstance().getBaseClass() != _playerClassId))
		{
			_playerClassId = getPlayerInstance().getClassId().getId();
		}
		return _playerClassId;
	}
	
	public int getPlayerLvl()
	{
		if (isOnline() && (getPlayerInstance().getLevel() != _playerLvl))
		{
			_playerLvl = getPlayerInstance().getLevel();
		}
		return _playerLvl;
	}
	
	public int getKarma()
	{
		return _karma;
	}
	
	public String getPlayerName()
	{
		if (isOnline() && !getPlayerInstance().getName().equalsIgnoreCase(_playerName))
		{
			_playerName = getPlayerInstance().getName();
		}
		return _playerName;
	}
	
	public PlayerInstance getPlayerInstance()
	{
		return World.getInstance().getPlayer(_playerId);
	}
	
	public boolean isOnline()
	{
		return (getPlayerInstance() != null) && (getPlayerInstance().isOnlineInt() > 0);
	}
}
