package org.l2jmobius.gameserver.model.holders;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.enums.Movie;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author St3eT
 */
public class MovieHolder
{
	private final Movie _movie;
	private final List<PlayerInstance> _players;
	private final Collection<PlayerInstance> _votedPlayers = ConcurrentHashMap.newKeySet();
	
	public MovieHolder(List<PlayerInstance> players, Movie movie)
	{
		_players = players;
		_movie = movie;
		_players.forEach(p -> p.playMovie(this));
	}
	
	public Movie getMovie()
	{
		return _movie;
	}
	
	public void playerEscapeVote(PlayerInstance player)
	{
		if (_votedPlayers.contains(player) || !_players.contains(player) || !_movie.isEscapable())
		{
			return;
		}
		
		_votedPlayers.add(player);
		
		if (((_votedPlayers.size() * 100) / _players.size()) >= 50)
		{
			_players.forEach(PlayerInstance::stopMovie);
		}
	}
	
	public List<PlayerInstance> getPlayers()
	{
		return _players;
	}
	
	public Collection<PlayerInstance> getVotedPlayers()
	{
		return _votedPlayers;
	}
}