package org.l2jmobius.gameserver.model;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.instancemanager.HandysBlockCheckerManager;
import org.l2jmobius.gameserver.instancemanager.games.BlockChecker;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * @author xban1x
 */
public class ArenaParticipantsHolder
{
	private final int _arena;
	private final List<PlayerInstance> _redPlayers;
	private final List<PlayerInstance> _bluePlayers;
	private final BlockChecker _engine;
	
	public ArenaParticipantsHolder(int arena)
	{
		_arena = arena;
		_redPlayers = new ArrayList<>(6);
		_bluePlayers = new ArrayList<>(6);
		_engine = new BlockChecker(this, _arena);
	}
	
	public List<PlayerInstance> getRedPlayers()
	{
		return _redPlayers;
	}
	
	public List<PlayerInstance> getBluePlayers()
	{
		return _bluePlayers;
	}
	
	public List<PlayerInstance> getAllPlayers()
	{
		final List<PlayerInstance> all = new ArrayList<>(12);
		all.addAll(_redPlayers);
		all.addAll(_bluePlayers);
		return all;
	}
	
	public void addPlayer(PlayerInstance player, int team)
	{
		if (team == 0)
		{
			_redPlayers.add(player);
		}
		else
		{
			_bluePlayers.add(player);
		}
	}
	
	public void removePlayer(PlayerInstance player, int team)
	{
		if (team == 0)
		{
			_redPlayers.remove(player);
		}
		else
		{
			_bluePlayers.remove(player);
		}
	}
	
	public int getPlayerTeam(PlayerInstance player)
	{
		if (_redPlayers.contains(player))
		{
			return 0;
		}
		else if (_bluePlayers.contains(player))
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	public int getRedTeamSize()
	{
		return _redPlayers.size();
	}
	
	public int getBlueTeamSize()
	{
		return _bluePlayers.size();
	}
	
	public void broadCastPacketToTeam(IClientOutgoingPacket packet)
	{
		for (PlayerInstance p : _redPlayers)
		{
			p.sendPacket(packet);
		}
		for (PlayerInstance p : _bluePlayers)
		{
			p.sendPacket(packet);
		}
	}
	
	public void clearPlayers()
	{
		_redPlayers.clear();
		_bluePlayers.clear();
	}
	
	public BlockChecker getEvent()
	{
		return _engine;
	}
	
	public void updateEvent()
	{
		_engine.updatePlayersOnStart(this);
	}
	
	public void checkAndShuffle()
	{
		final int redSize = _redPlayers.size();
		final int blueSize = _bluePlayers.size();
		if (redSize > (blueSize + 1))
		{
			broadCastPacketToTeam(new SystemMessage(SystemMessageId.TEAM_MEMBERS_WERE_MODIFIED_BECAUSE_THE_TEAMS_WERE_UNBALANCED));
			final int needed = redSize - (blueSize + 1);
			for (int i = 0; i < (needed + 1); i++)
			{
				final PlayerInstance plr = _redPlayers.get(i);
				if (plr == null)
				{
					continue;
				}
				HandysBlockCheckerManager.getInstance().changePlayerToTeam(plr, _arena);
			}
		}
		else if (blueSize > (redSize + 1))
		{
			broadCastPacketToTeam(new SystemMessage(SystemMessageId.TEAM_MEMBERS_WERE_MODIFIED_BECAUSE_THE_TEAMS_WERE_UNBALANCED));
			final int needed = blueSize - (redSize + 1);
			for (int i = 0; i < (needed + 1); i++)
			{
				final PlayerInstance plr = _bluePlayers.get(i);
				if (plr == null)
				{
					continue;
				}
				HandysBlockCheckerManager.getInstance().changePlayerToTeam(plr, _arena);
			}
		}
	}
}
