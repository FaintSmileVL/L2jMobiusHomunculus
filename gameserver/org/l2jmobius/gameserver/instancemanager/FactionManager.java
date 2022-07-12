package org.l2jmobius.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Contains objectId and factionId for all players.
 * @author Mobius
 */
public class FactionManager
{
	private static final Logger LOGGER = Logger.getLogger(FactionManager.class.getName());
	private final Map<Integer, Integer> _playerFactions = new ConcurrentHashMap<>();
	
	protected FactionManager()
	{
		loadAll();
	}
	
	private void loadAll()
	{
		_playerFactions.clear();
		try (Connection con = DatabaseFactory.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT charId, faction FROM characters"))
		{
			while (rs.next())
			{
				_playerFactions.put(rs.getInt(1), rs.getInt(2));
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Could not load character faction information: " + e.getMessage(), e);
		}
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _playerFactions.size() + " character faction values.");
	}
	
	public int getFactionByCharId(int id)
	{
		if (id <= 0)
		{
			return 0;
		}
		
		Integer factionId = _playerFactions.get(id);
		if (factionId != null)
		{
			return factionId;
		}
		
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT faction FROM characters WHERE charId=?"))
		{
			ps.setInt(1, id);
			try (ResultSet rset = ps.executeQuery())
			{
				if (rset.next())
				{
					factionId = rset.getInt(1);
					_playerFactions.put(id, factionId);
					return factionId;
				}
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Could not check existing char id: " + e.getMessage(), e);
		}
		
		return 0; // not found
	}
	
	public boolean isSameFaction(PlayerInstance player1, PlayerInstance player2)
	{
		// TODO: Maybe add support for multiple factions?
		return (player1.isGood() && player2.isGood()) || (player1.isEvil() && player2.isEvil());
	}
	
	public static FactionManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final FactionManager INSTANCE = new FactionManager();
	}
}
