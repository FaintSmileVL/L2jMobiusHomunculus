package org.l2jmobius.gameserver.model.clan;

/**
 * @author UnAfraid
 */
public class ClanInfo
{
	private final Clan _clan;
	private final int _total;
	private final int _online;
	
	public ClanInfo(Clan clan)
	{
		_clan = clan;
		_total = clan.getMembersCount();
		_online = clan.getOnlineMembersCount();
	}
	
	public Clan getClan()
	{
		return _clan;
	}
	
	public int getTotal()
	{
		return _total;
	}
	
	public int getOnline()
	{
		return _online;
	}
}
