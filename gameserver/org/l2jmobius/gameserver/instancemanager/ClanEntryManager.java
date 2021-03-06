package org.l2jmobius.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.data.DatabaseFactory;
import util.CommonUtil;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.model.clan.entry.PledgeApplicantInfo;
import org.l2jmobius.gameserver.model.clan.entry.PledgeRecruitInfo;
import org.l2jmobius.gameserver.model.clan.entry.PledgeWaitingInfo;

/**
 * @author Sdw
 */
public class ClanEntryManager
{
	protected static final Logger LOGGER = Logger.getLogger(ClanEntryManager.class.getName());
	
	private static final Map<Integer, PledgeWaitingInfo> _waitingList = new ConcurrentHashMap<>();
	private static final Map<Integer, PledgeRecruitInfo> _clanList = new ConcurrentHashMap<>();
	private static final Map<Integer, Map<Integer, PledgeApplicantInfo>> _applicantList = new ConcurrentHashMap<>();
	
	private static final Map<Integer, ScheduledFuture<?>> _clanLocked = new ConcurrentHashMap<>();
	private static final Map<Integer, ScheduledFuture<?>> _playerLocked = new ConcurrentHashMap<>();
	
	private static final String INSERT_APPLICANT = "REPLACE INTO pledge_applicant VALUES (?, ?, ?, ?)";
	private static final String DELETE_APPLICANT = "DELETE FROM pledge_applicant WHERE charId = ? AND clanId = ?";
	
	private static final String INSERT_WAITING_LIST = "INSERT INTO pledge_waiting_list VALUES (?, ?)";
	private static final String DELETE_WAITING_LIST = "DELETE FROM pledge_waiting_list WHERE char_id = ?";
	
	private static final String INSERT_CLAN_RECRUIT = "INSERT INTO pledge_recruit VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_CLAN_RECRUIT = "UPDATE pledge_recruit SET karma = ?, information = ?, detailed_information = ?, application_type = ?, recruit_type = ? WHERE clan_id = ?";
	private static final String DELETE_CLAN_RECRUIT = "DELETE FROM pledge_recruit WHERE clan_id = ?";
	
	//@formatter:off
	private static final List<Comparator<PledgeWaitingInfo>> PLAYER_COMPARATOR = Arrays.asList(
		null,
		Comparator.comparing(PledgeWaitingInfo::getPlayerName), 
		Comparator.comparingInt(PledgeWaitingInfo::getKarma), 
		Comparator.comparingInt(PledgeWaitingInfo::getPlayerLvl), 
		Comparator.comparingInt(PledgeWaitingInfo::getPlayerClassId));
	//@formatter:on
	
	//@formatter:off
	private static final List<Comparator<PledgeRecruitInfo>> CLAN_COMPARATOR = Arrays.asList(
		null,
		Comparator.comparing(PledgeRecruitInfo::getClanName),
		Comparator.comparing(PledgeRecruitInfo::getClanLeaderName),
		Comparator.comparingInt(PledgeRecruitInfo::getClanLevel),
		Comparator.comparingInt(PledgeRecruitInfo::getKarma));
	//@formatter:on
	
	private static final long LOCK_TIME = TimeUnit.MINUTES.toMillis(5);
	
	protected ClanEntryManager()
	{
		load();
	}
	
	private void load()
	{
		try (Connection con = DatabaseFactory.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM pledge_recruit"))
		{
			while (rs.next())
			{
				final int clanId = rs.getInt("clan_id");
				_clanList.put(clanId, new PledgeRecruitInfo(clanId, rs.getInt("karma"), rs.getString("information"), rs.getString("detailed_information"), rs.getInt("application_type"), rs.getInt("recruit_type")));
				// Remove non existing clan data.
				if (ClanTable.getInstance().getClan(clanId) == null)
				{
					removeFromClanList(clanId);
				}
			}
			LOGGER.info(getClass().getSimpleName() + ": Loaded " + _clanList.size() + " clan entries.");
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Failed to load: ", e);
		}
		
		try (Connection con = DatabaseFactory.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT a.char_id, a.karma, b.base_class, b.level, b.char_name FROM pledge_waiting_list as a LEFT JOIN characters as b ON a.char_id = b.charId"))
		{
			while (rs.next())
			{
				_waitingList.put(rs.getInt("char_id"), new PledgeWaitingInfo(rs.getInt("char_id"), rs.getInt("level"), rs.getInt("karma"), rs.getInt("base_class"), rs.getString("char_name")));
			}
			
			LOGGER.info(getClass().getSimpleName() + ": Loaded " + _waitingList.size() + " players in waiting list.");
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Failed to load: ", e);
		}
		
		try (Connection con = DatabaseFactory.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT a.charId, a.clanId, a.karma, a.message, b.base_class, b.level, b.char_name FROM pledge_applicant as a LEFT JOIN characters as b ON a.charId = b.charId"))
		{
			while (rs.next())
			{
				_applicantList.computeIfAbsent(rs.getInt("clanId"), k -> new ConcurrentHashMap<>()).put(rs.getInt("charId"), new PledgeApplicantInfo(rs.getInt("charId"), rs.getString("char_name"), rs.getInt("level"), rs.getInt("karma"), rs.getInt("clanId"), rs.getString("message")));
			}
			
			LOGGER.info(getClass().getSimpleName() + ": Loaded " + _applicantList.size() + " player applications.");
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Failed to load: ", e);
		}
	}
	
	public Map<Integer, PledgeWaitingInfo> getWaitingList()
	{
		return _waitingList;
	}
	
	public Map<Integer, PledgeRecruitInfo> getClanList()
	{
		return _clanList;
	}
	
	public Map<Integer, Map<Integer, PledgeApplicantInfo>> getApplicantList()
	{
		return _applicantList;
	}
	
	public Map<Integer, PledgeApplicantInfo> getApplicantListForClan(int clanId)
	{
		return _applicantList.getOrDefault(clanId, Collections.emptyMap());
	}
	
	public PledgeApplicantInfo getPlayerApplication(int clanId, int playerId)
	{
		return _applicantList.getOrDefault(clanId, Collections.emptyMap()).get(playerId);
	}
	
	public boolean removePlayerApplication(int clanId, int playerId)
	{
		final Map<Integer, PledgeApplicantInfo> clanApplicantList = _applicantList.get(clanId);
		
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement statement = con.prepareStatement(DELETE_APPLICANT))
		{
			statement.setInt(1, playerId);
			statement.setInt(2, clanId);
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		return (clanApplicantList != null) && (clanApplicantList.remove(playerId) != null);
	}
	
	public boolean addPlayerApplicationToClan(int clanId, PledgeApplicantInfo info)
	{
		if (!_playerLocked.containsKey(info.getPlayerId()))
		{
			_applicantList.computeIfAbsent(clanId, k -> new ConcurrentHashMap<>()).put(info.getPlayerId(), info);
			
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement(INSERT_APPLICANT))
			{
				statement.setInt(1, info.getPlayerId());
				statement.setInt(2, info.getRequestClanId());
				statement.setInt(3, info.getKarma());
				statement.setString(4, info.getMessage());
				statement.executeUpdate();
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
			return true;
		}
		return false;
	}
	
	public Integer getClanIdForPlayerApplication(int playerId)
	{
		for (Entry<Integer, Map<Integer, PledgeApplicantInfo>> entry : _applicantList.entrySet())
		{
			if (entry.getValue().containsKey(playerId))
			{
				return entry.getKey();
			}
		}
		return 0;
	}
	
	public boolean addToWaitingList(int playerId, PledgeWaitingInfo info)
	{
		if (!_playerLocked.containsKey(playerId))
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement(INSERT_WAITING_LIST))
			{
				statement.setInt(1, info.getPlayerId());
				statement.setInt(2, info.getKarma());
				statement.executeUpdate();
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
			_waitingList.put(playerId, info);
			return true;
		}
		return false;
	}
	
	public boolean removeFromWaitingList(int playerId)
	{
		if (_waitingList.containsKey(playerId))
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement(DELETE_WAITING_LIST))
			{
				statement.setInt(1, playerId);
				statement.executeUpdate();
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
			_waitingList.remove(playerId);
			lockPlayer(playerId);
			return true;
		}
		return false;
	}
	
	public boolean addToClanList(int clanId, PledgeRecruitInfo info)
	{
		if (!_clanList.containsKey(clanId) && !_clanLocked.containsKey(clanId))
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement(INSERT_CLAN_RECRUIT))
			{
				statement.setInt(1, info.getClanId());
				statement.setInt(2, info.getKarma());
				statement.setString(3, info.getInformation());
				statement.setString(4, info.getDetailedInformation());
				statement.setInt(5, info.getApplicationType());
				statement.setInt(6, info.getRecruitType());
				statement.executeUpdate();
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
			_clanList.put(clanId, info);
			return true;
		}
		return false;
	}
	
	public boolean updateClanList(int clanId, PledgeRecruitInfo info)
	{
		if (_clanList.containsKey(clanId) && !_clanLocked.containsKey(clanId))
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement(UPDATE_CLAN_RECRUIT))
			{
				statement.setInt(1, info.getKarma());
				statement.setString(2, info.getInformation());
				statement.setString(3, info.getDetailedInformation());
				statement.setInt(4, info.getApplicationType());
				statement.setInt(5, info.getRecruitType());
				statement.setInt(6, info.getClanId());
				statement.executeUpdate();
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
			return _clanList.replace(clanId, info) != null;
		}
		return false;
	}
	
	public boolean removeFromClanList(int clanId)
	{
		if (_clanList.containsKey(clanId))
		{
			try (Connection con = DatabaseFactory.getConnection();
				PreparedStatement statement = con.prepareStatement(DELETE_CLAN_RECRUIT))
			{
				statement.setInt(1, clanId);
				statement.executeUpdate();
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
			_clanList.remove(clanId);
			lockClan(clanId);
			return true;
		}
		return false;
	}
	
	public List<PledgeWaitingInfo> getSortedWaitingList(int levelMin, int levelMax, int role, int sortByValue, boolean descending)
	{
		final int sortBy = CommonUtil.constrain(sortByValue, 1, PLAYER_COMPARATOR.size() - 1);
		
		// TODO: Handle Role
		//@formatter:off
		return _waitingList.values().stream()
		      .filter(p -> ((p.getPlayerLvl() >= levelMin) && (p.getPlayerLvl() <= levelMax)))
		      .sorted(descending ? PLAYER_COMPARATOR.get(sortBy).reversed() : PLAYER_COMPARATOR.get(sortBy))
		      .collect(Collectors.toList());
		//@formatter:on
	}
	
	public List<PledgeWaitingInfo> queryWaitingListByName(String name)
	{
		final List<PledgeWaitingInfo> result = new ArrayList<>();
		for (PledgeWaitingInfo p : _waitingList.values())
		{
			if (p.getPlayerName().toLowerCase().contains(name))
			{
				result.add(p);
			}
		}
		return result;
	}
	
	public List<PledgeRecruitInfo> getSortedClanListByName(String query, int type)
	{
		final List<PledgeRecruitInfo> result = new ArrayList<>();
		if (type == 1)
		{
			for (PledgeRecruitInfo p : _clanList.values())
			{
				if (p.getClanName().toLowerCase().contains(query))
				{
					result.add(p);
				}
			}
		}
		else
		{
			for (PledgeRecruitInfo p : _clanList.values())
			{
				if (p.getClanLeaderName().toLowerCase().contains(query))
				{
					result.add(p);
				}
			}
		}
		return result;
	}
	
	public PledgeRecruitInfo getClanById(int clanId)
	{
		return _clanList.get(clanId);
	}
	
	public boolean isClanRegistred(int clanId)
	{
		return _clanList.get(clanId) != null;
	}
	
	public boolean isPlayerRegistred(int playerId)
	{
		return _waitingList.get(playerId) != null;
	}
	
	public List<PledgeRecruitInfo> getUnSortedClanList()
	{
		return _clanList.values().stream().collect(Collectors.toList());
	}
	
	public List<PledgeRecruitInfo> getSortedClanList(int clanLevel, int karma, int sortByValue, boolean descending)
	{
		final int sortBy = CommonUtil.constrain(sortByValue, 1, CLAN_COMPARATOR.size() - 1);
		
		//@formatter:off
		return _clanList.values().stream()
		      .filter((p -> (((clanLevel < 0) && (karma >= 0) && (karma != p.getKarma())) || ((clanLevel >= 0) && (karma < 0) && (clanLevel != (p.getClan() != null ? p.getClanLevel() : 0))) || ((clanLevel >= 0) && (karma >= 0) && ((clanLevel != (p.getClan() != null ? p.getClanLevel() : 0)) || (karma != p.getKarma()))))))
		      .sorted(descending ? CLAN_COMPARATOR.get(sortBy).reversed() : CLAN_COMPARATOR.get(sortBy))
		      .collect(Collectors.toList());
		//@formatter:on
	}
	
	public long getPlayerLockTime(int playerId)
	{
		return _playerLocked.get(playerId) == null ? 0 : _playerLocked.get(playerId).getDelay(TimeUnit.MINUTES);
	}
	
	public long getClanLockTime(int playerId)
	{
		return _clanLocked.get(playerId) == null ? 0 : _clanLocked.get(playerId).getDelay(TimeUnit.MINUTES);
	}
	
	private void lockPlayer(int playerId)
	{
		_playerLocked.put(playerId, ThreadPool.schedule(() -> _playerLocked.remove(playerId), LOCK_TIME));
	}
	
	private void lockClan(int clanId)
	{
		_clanLocked.put(clanId, ThreadPool.schedule(() -> _clanLocked.remove(clanId), LOCK_TIME));
	}
	
	public static ClanEntryManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ClanEntryManager INSTANCE = new ClanEntryManager();
	}
}
