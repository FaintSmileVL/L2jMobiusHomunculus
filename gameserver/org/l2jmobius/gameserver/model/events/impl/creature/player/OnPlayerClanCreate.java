package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnPlayerClanCreate implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Clan _clan;
	
	public OnPlayerClanCreate(PlayerInstance player, Clan clan)
	{
		_player = player;
		_clan = clan;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public Clan getClan()
	{
		return _clan;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_CLAN_CREATE;
	}
}
