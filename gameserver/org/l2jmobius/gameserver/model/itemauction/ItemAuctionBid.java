package org.l2jmobius.gameserver.model.itemauction;

import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author Forsaiken
 */
public class ItemAuctionBid
{
	private final int _playerObjId;
	private long _lastBid;
	
	public ItemAuctionBid(int playerObjId, long lastBid)
	{
		_playerObjId = playerObjId;
		_lastBid = lastBid;
	}
	
	public int getPlayerObjId()
	{
		return _playerObjId;
	}
	
	public long getLastBid()
	{
		return _lastBid;
	}
	
	final void setLastBid(long lastBid)
	{
		_lastBid = lastBid;
	}
	
	final void cancelBid()
	{
		_lastBid = -1;
	}
	
	final boolean isCanceled()
	{
		return _lastBid <= 0;
	}
	
	final PlayerInstance getPlayer()
	{
		return World.getInstance().getPlayer(_playerObjId);
	}
}