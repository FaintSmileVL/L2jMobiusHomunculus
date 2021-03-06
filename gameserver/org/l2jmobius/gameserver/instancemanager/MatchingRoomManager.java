package org.l2jmobius.gameserver.instancemanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.l2jmobius.gameserver.enums.ClassId;
import org.l2jmobius.gameserver.enums.MatchingRoomType;
import org.l2jmobius.gameserver.enums.PartyMatchingRoomLevelType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.matching.MatchingRoom;

/**
 * @author Sdw
 */
public class MatchingRoomManager
{
	private Set<PlayerInstance> _waitingList;
	
	private static final Map<MatchingRoomType, Map<Integer, MatchingRoom>> _rooms = new ConcurrentHashMap<>(2);
	
	private final AtomicInteger _id = new AtomicInteger(0);
	
	public void addToWaitingList(PlayerInstance player)
	{
		if (_waitingList == null)
		{
			synchronized (this)
			{
				if (_waitingList == null)
				{
					_waitingList = ConcurrentHashMap.newKeySet(1);
				}
			}
		}
		_waitingList.add(player);
	}
	
	public void removeFromWaitingList(PlayerInstance player)
	{
		getPlayerInWaitingList().remove(player);
	}
	
	public Set<PlayerInstance> getPlayerInWaitingList()
	{
		return _waitingList == null ? Collections.emptySet() : _waitingList;
	}
	
	public List<PlayerInstance> getPlayerInWaitingList(int minLevel, int maxLevel, List<ClassId> classIds, String query)
	{
		if (_waitingList == null)
		{
			return Collections.emptyList();
		}
		return _waitingList.stream() //
			.filter(p -> (p != null) //
				&& (p.getLevel() >= minLevel) //
				&& (p.getLevel() <= maxLevel)) //
			.filter(p -> (classIds == null) //
				|| classIds.contains(p.getClassId())) //
			.filter(p -> (query == null) //
				|| query.isEmpty() //
				|| p.getName().toLowerCase().contains(query)) //
			.collect(Collectors.toList());
	}
	
	public int addMatchingRoom(MatchingRoom room)
	{
		final int roomId = _id.incrementAndGet();
		_rooms.computeIfAbsent(room.getRoomType(), k -> new ConcurrentHashMap<>()).put(roomId, room);
		return roomId;
	}
	
	public void removeMatchingRoom(MatchingRoom room)
	{
		_rooms.getOrDefault(room.getRoomType(), Collections.emptyMap()).remove(room.getId());
	}
	
	public Map<Integer, MatchingRoom> getPartyMathchingRooms()
	{
		return _rooms.get(MatchingRoomType.PARTY);
	}
	
	public List<MatchingRoom> getPartyMathchingRooms(int location, PartyMatchingRoomLevelType type, int requestorLevel)
	{
		final List<MatchingRoom> result = new ArrayList<>();
		if (_rooms.containsKey(MatchingRoomType.PARTY))
		{
			for (MatchingRoom room : _rooms.get(MatchingRoomType.PARTY).values())
			{
				if (((location < 0) || (room.getLocation() == location)) //
					&& ((type == PartyMatchingRoomLevelType.ALL) || ((room.getMinLevel() >= requestorLevel) && (room.getMaxLevel() <= requestorLevel))))
				{
					result.add(room);
				}
			}
		}
		return result;
	}
	
	public Map<Integer, MatchingRoom> getCCMathchingRooms()
	{
		return _rooms.get(MatchingRoomType.COMMAND_CHANNEL);
	}
	
	public List<MatchingRoom> getCCMathchingRooms(int location, int level)
	{
		final List<MatchingRoom> result = new ArrayList<>();
		if (_rooms.containsKey(MatchingRoomType.COMMAND_CHANNEL))
		{
			for (MatchingRoom room : _rooms.get(MatchingRoomType.COMMAND_CHANNEL).values())
			{
				if ((room.getLocation() == location) //
					&& ((room.getMinLevel() <= level) && (room.getMaxLevel() >= level)))
				{
					result.add(room);
				}
			}
		}
		return result;
	}
	
	public MatchingRoom getCCMatchingRoom(int roomId)
	{
		return _rooms.getOrDefault(MatchingRoomType.COMMAND_CHANNEL, Collections.emptyMap()).get(roomId);
	}
	
	public MatchingRoom getPartyMathchingRoom(int location, int level)
	{
		if (_rooms.containsKey(MatchingRoomType.PARTY))
		{
			for (MatchingRoom room : _rooms.get(MatchingRoomType.PARTY).values())
			{
				if ((room.getLocation() == location) //
					&& ((room.getMinLevel() <= level) && (room.getMaxLevel() >= level)))
				{
					return room;
				}
			}
		}
		return null;
	}
	
	public MatchingRoom getPartyMathchingRoom(int roomId)
	{
		return _rooms.getOrDefault(MatchingRoomType.PARTY, Collections.emptyMap()).get(roomId);
	}
	
	public static MatchingRoomManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final MatchingRoomManager INSTANCE = new MatchingRoomManager();
	}
}
