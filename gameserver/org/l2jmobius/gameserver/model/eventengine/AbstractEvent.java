package org.l2jmobius.gameserver.model.eventengine;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.AbstractScript;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 * @param <T>
 */
public abstract class AbstractEvent<T extends AbstractEventMember<?>>extends AbstractScript
{
	private final Map<Integer, T> _members = new ConcurrentHashMap<>();
	private IEventState _state;
	
	public Map<Integer, T> getMembers()
	{
		final Map<Integer, T> members = new HashMap<>();
		for (Entry<Integer, T> entry : _members.entrySet())
		{
			final T member = entry.getValue();
			if (member != null)
			{
				final PlayerInstance player = member.getPlayer();
				if ((player != null) && player.isOnline() && !player.isInOfflineMode())
				{
					members.putIfAbsent(entry.getKey(), member);
				}
			}
		}
		return members;
	}
	
	public T getMember(int objectId)
	{
		return getMembers().get(objectId);
	}
	
	public void addMember(T member)
	{
		_members.put(member.getObjectId(), member);
	}
	
	public void removeMember(int objectId)
	{
		_members.remove(objectId);
	}
	
	public void clearMembers()
	{
		_members.clear();
	}
	
	public void broadcastPacket(IClientOutgoingPacket... packets)
	{
		_members.values().forEach(member -> member.sendPacket(packets));
	}
	
	public IEventState getState()
	{
		return _state;
	}
	
	public void setState(IEventState state)
	{
		_state = state;
	}
	
	@Override
	public String getScriptName()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public Path getScriptPath()
	{
		return null;
	}
	
	/**
	 * @param player
	 * @return {@code true} if player is on event, {@code false} otherwise.
	 */
	public boolean isOnEvent(PlayerInstance player)
	{
		return _members.containsKey(player.getObjectId());
	}
	
	/**
	 * @param player
	 * @return {@code true} if player is blocked from leaving the game, {@code false} otherwise.
	 */
	public boolean isBlockingExit(PlayerInstance player)
	{
		return false;
	}
	
	/**
	 * @param player
	 * @return {@code true} if player is blocked from receiving death penalty upon death, {@code false} otherwise.
	 */
	public boolean isBlockingDeathPenalty(PlayerInstance player)
	{
		return false;
	}
	
	/**
	 * @param player
	 * @return {@code true} if player can revive after death, {@code false} otherwise.
	 */
	public boolean canRevive(PlayerInstance player)
	{
		return true;
	}
}
