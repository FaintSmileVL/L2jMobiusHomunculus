package org.l2jmobius.gameserver.model.residences;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.l2jmobius.gameserver.model.clan.Clan;

/**
 * @author Sdw
 */
public class Bidder
{
	private final Clan _clan;
	private final long _bid;
	private final long _time;
	
	public Bidder(Clan clan, long bid, long time)
	{
		_clan = clan;
		_bid = bid;
		_time = time == 0 ? Instant.now().toEpochMilli() : time;
	}
	
	public Clan getClan()
	{
		return _clan;
	}
	
	public String getClanName()
	{
		return _clan.getName();
	}
	
	public String getLeaderName()
	{
		return _clan.getLeaderName();
	}
	
	public long getBid()
	{
		return _bid;
	}
	
	public long getTime()
	{
		return _time;
	}
	
	public String getFormattedTime()
	{
		return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(Instant.ofEpochMilli(_time).atZone(ZoneId.systemDefault()));
	}
}
