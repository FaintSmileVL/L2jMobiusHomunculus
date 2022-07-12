package org.l2jmobius.gameserver.model.events.impl.creature.npc;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author St3eT
 */
public class OnNpcMenuSelect implements IBaseEvent
{
	private final PlayerInstance _player;
	private final Npc _npc;
	private final int _ask;
	private final int _reply;
	
	/**
	 * @param player
	 * @param npc
	 * @param ask
	 * @param reply
	 */
	public OnNpcMenuSelect(PlayerInstance player, Npc npc, int ask, int reply)
	{
		_player = player;
		_npc = npc;
		_ask = ask;
		_reply = reply;
	}
	
	public PlayerInstance getTalker()
	{
		return _player;
	}
	
	public Npc getNpc()
	{
		return _npc;
	}
	
	public int getAsk()
	{
		return _ask;
	}
	
	public int getReply()
	{
		return _reply;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_NPC_MENU_SELECT;
	}
}
