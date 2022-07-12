package org.l2jmobius.gameserver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class Mentee
{
	private static final Logger LOGGER = Logger.getLogger(Mentee.class.getName());
	
	private final int _objectId;
	private String _name;
	private int _classId;
	private int _currentLevel;
	
	public Mentee(int objectId)
	{
		_objectId = objectId;
		load();
	}
	
	public void load()
	{
		final PlayerInstance player = getPlayerInstance();
		if (player == null) // Only if player is offline
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement("SELECT char_name, level, base_class FROM characters WHERE charId = ?"))
			{
				statement.setInt(1, _objectId);
				try (ResultSet rset = statement.executeQuery())
				{
					if (rset.next())
					{
						_name = rset.getString("char_name");
						_classId = rset.getInt("base_class");
						_currentLevel = rset.getInt("level");
					}
				}
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
		}
		else
		{
			_name = player.getName();
			_classId = player.getBaseClass();
			_currentLevel = player.getLevel();
		}
	}
	
	public int getObjectId()
	{
		return _objectId;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public int getClassId()
	{
		if (isOnline() && (getPlayerInstance().getClassId().getId() != _classId))
		{
			_classId = getPlayerInstance().getClassId().getId();
		}
		return _classId;
	}
	
	public int getLevel()
	{
		if (isOnline() && (getPlayerInstance().getLevel() != _currentLevel))
		{
			_currentLevel = getPlayerInstance().getLevel();
		}
		return _currentLevel;
	}
	
	public PlayerInstance getPlayerInstance()
	{
		return World.getInstance().getPlayer(_objectId);
	}
	
	public boolean isOnline()
	{
		return (getPlayerInstance() != null) && (getPlayerInstance().isOnlineInt() > 0);
	}
	
	public int isOnlineInt()
	{
		return isOnline() ? getPlayerInstance().isOnlineInt() : 0;
	}
	
	public void sendPacket(IClientOutgoingPacket packet)
	{
		if (isOnline())
		{
			getPlayerInstance().sendPacket(packet);
		}
	}
}
