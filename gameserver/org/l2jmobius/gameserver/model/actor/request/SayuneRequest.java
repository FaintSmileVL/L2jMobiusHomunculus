package org.l2jmobius.gameserver.model.actor.request;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.l2jmobius.gameserver.data.xml.SayuneData;
import org.l2jmobius.gameserver.enums.SayuneType;
import org.l2jmobius.gameserver.model.SayuneEntry;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.sayune.ExFlyMove;
import org.l2jmobius.gameserver.network.serverpackets.sayune.ExFlyMoveBroadcast;
import org.l2jmobius.gameserver.util.Broadcast;

/**
 * @author UnAfraid
 */
public class SayuneRequest extends AbstractRequest
{
	private final int _mapId;
	private boolean _isSelecting;
	private final Deque<SayuneEntry> _possibleEntries = new LinkedList<>();
	
	public SayuneRequest(PlayerInstance player, int mapId)
	{
		super(player);
		_mapId = mapId;
		
		final SayuneEntry map = SayuneData.getInstance().getMap(_mapId);
		Objects.requireNonNull(map);
		
		_possibleEntries.addAll(map.getInnerEntries());
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		return false;
	}
	
	private SayuneEntry findEntry(int pos)
	{
		if (_possibleEntries.isEmpty())
		{
			return null;
		}
		else if (_isSelecting)
		{
			for (SayuneEntry entry : _possibleEntries)
			{
				if (entry.getId() == pos)
				{
					return entry;
				}
			}
			return null;
		}
		return _possibleEntries.removeFirst();
	}
	
	public synchronized void move(PlayerInstance player, int pos)
	{
		final SayuneEntry map = SayuneData.getInstance().getMap(_mapId);
		if ((map == null) || map.getInnerEntries().isEmpty())
		{
			player.sendMessage("MapId: " + _mapId + " was not found in the map!");
			return;
		}
		
		final SayuneEntry nextEntry = findEntry(pos);
		if (nextEntry == null)
		{
			player.removeRequest(getClass());
			return;
		}
		
		// If player was selecting unset and set his possible path
		if (_isSelecting)
		{
			_isSelecting = false;
			
			// Set next possible path
			if (!nextEntry.isSelector())
			{
				_possibleEntries.clear();
				_possibleEntries.addAll(nextEntry.getInnerEntries());
			}
		}
		
		final SayuneType type = (pos == 0) && nextEntry.isSelector() ? SayuneType.START_LOC : nextEntry.isSelector() ? SayuneType.MULTI_WAY_LOC : SayuneType.ONE_WAY_LOC;
		final List<SayuneEntry> locations = nextEntry.isSelector() ? nextEntry.getInnerEntries() : Arrays.asList(nextEntry);
		if (nextEntry.isSelector())
		{
			_possibleEntries.clear();
			_possibleEntries.addAll(locations);
			_isSelecting = true;
		}
		
		player.sendPacket(new ExFlyMove(player, type, _mapId, locations));
		
		final SayuneEntry activeEntry = locations.get(0);
		Broadcast.toKnownPlayersInRadius(player, new ExFlyMoveBroadcast(player, type, map.getId(), activeEntry), 1000);
		player.setXYZ(activeEntry);
	}
	
	public void onLogout()
	{
		final SayuneEntry map = SayuneData.getInstance().getMap(_mapId);
		if ((map != null) && !map.getInnerEntries().isEmpty())
		{
			final SayuneEntry nextEntry = findEntry(0);
			if (_isSelecting || ((nextEntry != null) && nextEntry.isSelector()))
			{
				// If player is on selector or next entry is selector go back to first entry
				getActiveChar().setXYZ(map);
			}
			else
			{
				// Try to find last entry to set player, if not set him to first entry
				final SayuneEntry lastEntry = map.getInnerEntries().get(map.getInnerEntries().size() - 1);
				if (lastEntry != null)
				{
					getActiveChar().setXYZ(lastEntry);
				}
				else
				{
					getActiveChar().setXYZ(map);
				}
			}
		}
	}
}
