package org.l2jmobius.gameserver.model.events.impl.clan;

import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnClanWarFinish implements IBaseEvent
{
	private final Clan _clan1;
	private final Clan _clan2;
	
	public OnClanWarFinish(Clan clan1, Clan clan2)
	{
		_clan1 = clan1;
		_clan2 = clan2;
	}
	
	public Clan getClan1()
	{
		return _clan1;
	}
	
	public Clan getClan2()
	{
		return _clan2;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CLAN_WAR_FINISH;
	}
}
