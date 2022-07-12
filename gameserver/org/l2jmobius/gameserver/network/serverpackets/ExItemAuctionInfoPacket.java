package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.itemauction.ItemAuction;
import org.l2jmobius.gameserver.model.itemauction.ItemAuctionBid;
import org.l2jmobius.gameserver.model.itemauction.ItemAuctionState;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Forsaiken
 */
public class ExItemAuctionInfoPacket extends AbstractItemPacket
{
	private final boolean _refresh;
	private final int _timeRemaining;
	private final ItemAuction _currentAuction;
	private final ItemAuction _nextAuction;
	
	public ExItemAuctionInfoPacket(boolean refresh, ItemAuction currentAuction, ItemAuction nextAuction)
	{
		if (currentAuction == null)
		{
			throw new NullPointerException();
		}
		
		if (currentAuction.getAuctionState() != ItemAuctionState.STARTED)
		{
			_timeRemaining = 0;
		}
		else
		{
			_timeRemaining = (int) (currentAuction.getFinishingTimeRemaining() / 1000); // in seconds
		}
		
		_refresh = refresh;
		_currentAuction = currentAuction;
		_nextAuction = nextAuction;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ITEM_AUCTION_INFO.writeId(packet);
		
		packet.writeC(_refresh ? 0x00 : 0x01);
		packet.writeD(_currentAuction.getInstanceId());
		
		final ItemAuctionBid highestBid = _currentAuction.getHighestBid();
		packet.writeQ(highestBid != null ? highestBid.getLastBid() : _currentAuction.getAuctionInitBid());
		
		packet.writeD(_timeRemaining);
		writeItem(packet, _currentAuction.getItemInfo());
		
		if (_nextAuction != null)
		{
			packet.writeQ(_nextAuction.getAuctionInitBid());
			packet.writeD((int) (_nextAuction.getStartingTime() / 1000)); // unix time in seconds
			writeItem(packet, _nextAuction.getItemInfo());
		}
		return true;
	}
}
