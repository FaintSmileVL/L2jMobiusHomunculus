package org.l2jmobius.gameserver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.instancemanager.IdManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author evill33t
 */
public class Couple
{
	private static final Logger LOGGER = Logger.getLogger(Couple.class.getName());
	
	private int _id = 0;
	private int _player1Id = 0;
	private int _player2Id = 0;
	private boolean _maried = false;
	private Calendar _affiancedDate;
	private Calendar _weddingDate;
	
	public Couple(int coupleId)
	{
		_id = coupleId;
		
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM mods_wedding WHERE id = ?"))
		{
			ps.setInt(1, _id);
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					_player1Id = rs.getInt("player1Id");
					_player2Id = rs.getInt("player2Id");
					_maried = rs.getBoolean("married");
					_affiancedDate = Calendar.getInstance();
					_affiancedDate.setTimeInMillis(rs.getLong("affianceDate"));
					
					_weddingDate = Calendar.getInstance();
					_weddingDate.setTimeInMillis(rs.getLong("weddingDate"));
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Exception: Couple.load(): " + e.getMessage(), e);
		}
	}
	
	public Couple(PlayerInstance player1, PlayerInstance player2)
	{
		final int _tempPlayer1Id = player1.getObjectId();
		final int _tempPlayer2Id = player2.getObjectId();
		_player1Id = _tempPlayer1Id;
		_player2Id = _tempPlayer2Id;
		_affiancedDate = Calendar.getInstance();
		_affiancedDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
		
		_weddingDate = Calendar.getInstance();
		_weddingDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
		
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO mods_wedding (id, player1Id, player2Id, married, affianceDate, weddingDate) VALUES (?, ?, ?, ?, ?, ?)"))
		{
			_id = IdManager.getInstance().getNextId();
			ps.setInt(1, _id);
			ps.setInt(2, _player1Id);
			ps.setInt(3, _player2Id);
			ps.setBoolean(4, false);
			ps.setLong(5, _affiancedDate.getTimeInMillis());
			ps.setLong(6, _weddingDate.getTimeInMillis());
			ps.execute();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Could not create couple: " + e.getMessage(), e);
		}
	}
	
	public void marry()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE mods_wedding set married = ?, weddingDate = ? where id = ?"))
		{
			ps.setBoolean(1, true);
			_weddingDate = Calendar.getInstance();
			ps.setLong(2, _weddingDate.getTimeInMillis());
			ps.setInt(3, _id);
			ps.execute();
			_maried = true;
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Could not marry: " + e.getMessage(), e);
		}
	}
	
	public void divorce()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM mods_wedding WHERE id=?"))
		{
			ps.setInt(1, _id);
			ps.execute();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Exception: Couple.divorce(): " + e.getMessage(), e);
		}
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getPlayer1Id()
	{
		return _player1Id;
	}
	
	public int getPlayer2Id()
	{
		return _player2Id;
	}
	
	public boolean getMaried()
	{
		return _maried;
	}
	
	public Calendar getAffiancedDate()
	{
		return _affiancedDate;
	}
	
	public Calendar getWeddingDate()
	{
		return _weddingDate;
	}
}
