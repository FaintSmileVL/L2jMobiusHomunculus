package org.l2jmobius.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import util.Chronos;
import org.l2jmobius.gameserver.model.Mentee;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;

/**
 * @author UnAfraid
 */
public class MentorManager
{
	private static final Logger LOGGER = Logger.getLogger(MentorManager.class.getName());
	
	private final Map<Integer, Map<Integer, Mentee>> _menteeData = new ConcurrentHashMap<>();
	private final Map<Integer, Mentee> _mentors = new ConcurrentHashMap<>();
	
	protected MentorManager()
	{
		load();
	}
	
	private void load()
	{
		try (Connection con = DatabaseFactory.getConnection();
			Statement statement = con.createStatement();
			ResultSet rset = statement.executeQuery("SELECT * FROM character_mentees"))
		{
			while (rset.next())
			{
				addMentor(rset.getInt("mentorId"), rset.getInt("charId"));
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * Removes mentee for current PlayerInstance
	 * @param mentorId
	 * @param menteeId
	 */
	public void deleteMentee(int mentorId, int menteeId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement statement = con.prepareStatement("DELETE FROM character_mentees WHERE mentorId = ? AND charId = ?"))
		{
			statement.setInt(1, mentorId);
			statement.setInt(2, menteeId);
			statement.execute();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
	}
	
	/**
	 * @param mentorId
	 * @param menteeId
	 */
	public void deleteMentor(int mentorId, int menteeId)
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement statement = con.prepareStatement("DELETE FROM character_mentees WHERE mentorId = ? AND charId = ?"))
		{
			statement.setInt(1, mentorId);
			statement.setInt(2, menteeId);
			statement.execute();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		finally
		{
			removeMentor(mentorId, menteeId);
		}
	}
	
	public boolean isMentor(int objectId)
	{
		return _menteeData.containsKey(objectId);
	}
	
	public boolean isMentee(int objectId)
	{
		return _menteeData.values().stream().anyMatch(map -> map.containsKey(objectId));
	}
	
	public Map<Integer, Map<Integer, Mentee>> getMentorData()
	{
		return _menteeData;
	}
	
	public void cancelAllMentoringBuffs(PlayerInstance player)
	{
		if (player == null)
		{
			return;
		}
		
		for (BuffInfo info : player.getEffectList().getEffects())
		{
			if (info.getSkill().isMentoring())
			{
				player.stopSkillEffects(info.getSkill());
			}
		}
	}
	
	public void setPenalty(int mentorId, long penalty)
	{
		final PlayerInstance player = World.getInstance().getPlayer(mentorId);
		final PlayerVariables vars = player != null ? player.getVariables() : new PlayerVariables(mentorId);
		vars.set("Mentor-Penalty-" + mentorId, String.valueOf(Chronos.currentTimeMillis() + penalty));
	}
	
	public long getMentorPenalty(int mentorId)
	{
		final PlayerInstance player = World.getInstance().getPlayer(mentorId);
		final PlayerVariables vars = player != null ? player.getVariables() : new PlayerVariables(mentorId);
		return vars.getLong("Mentor-Penalty-" + mentorId, 0);
	}
	
	/**
	 * @param mentorId
	 * @param menteeId
	 */
	public void addMentor(int mentorId, int menteeId)
	{
		final Map<Integer, Mentee> mentees = _menteeData.computeIfAbsent(mentorId, map -> new ConcurrentHashMap<>());
		if (mentees.containsKey(menteeId))
		{
			mentees.get(menteeId).load(); // Just reloading data if is already there
		}
		else
		{
			mentees.put(menteeId, new Mentee(menteeId));
		}
	}
	
	/**
	 * @param mentorId
	 * @param menteeId
	 */
	public void removeMentor(int mentorId, int menteeId)
	{
		if (_menteeData.containsKey(mentorId))
		{
			_menteeData.get(mentorId).remove(menteeId);
			if (_menteeData.get(mentorId).isEmpty())
			{
				_menteeData.remove(mentorId);
				_mentors.remove(mentorId);
			}
		}
	}
	
	/**
	 * @param menteeId
	 * @return
	 */
	public Mentee getMentor(int menteeId)
	{
		for (Entry<Integer, Map<Integer, Mentee>> map : _menteeData.entrySet())
		{
			if (map.getValue().containsKey(menteeId))
			{
				if (!_mentors.containsKey(map.getKey()))
				{
					_mentors.put(map.getKey(), new Mentee(map.getKey()));
				}
				return _mentors.get(map.getKey());
			}
		}
		return null;
	}
	
	public Collection<Mentee> getMentees(int mentorId)
	{
		if (_menteeData.containsKey(mentorId))
		{
			return _menteeData.get(mentorId).values();
		}
		return Collections.emptyList();
	}
	
	/**
	 * @param mentorId
	 * @param menteeId
	 * @return
	 */
	public Mentee getMentee(int mentorId, int menteeId)
	{
		if (_menteeData.containsKey(mentorId))
		{
			return _menteeData.get(mentorId).get(menteeId);
		}
		return null;
	}
	
	public boolean isAllMenteesOffline(int menteorId, int menteeId)
	{
		boolean isAllMenteesOffline = true;
		for (Mentee men : getMentees(menteorId))
		{
			if (men.isOnline() && (men.getObjectId() != menteeId))
			{
				isAllMenteesOffline = false;
				break;
			}
		}
		return isAllMenteesOffline;
	}
	
	public boolean hasOnlineMentees(int menteorId)
	{
		for (Mentee mentee : getMentees(menteorId))
		{
			if ((mentee != null) && mentee.isOnline())
			{
				return true;
			}
		}
		return false;
	}
	
	public static MentorManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final MentorManager INSTANCE = new MentorManager();
	}
}
