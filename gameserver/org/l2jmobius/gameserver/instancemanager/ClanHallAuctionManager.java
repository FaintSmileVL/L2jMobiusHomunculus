package org.l2jmobius.gameserver.instancemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.l2jmobius.gameserver.data.xml.ClanHallData;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.eventengine.AbstractEvent;
import org.l2jmobius.gameserver.model.eventengine.AbstractEventManager;
import org.l2jmobius.gameserver.model.eventengine.ScheduleTarget;
import org.l2jmobius.gameserver.model.residences.ClanHallAuction;

/**
 * @author Sdw
 */
public class ClanHallAuctionManager extends AbstractEventManager<AbstractEvent<?>>
{
	private static final Logger LOGGER = Logger.getLogger(ClanHallAuctionManager.class.getName());
	
	private static final Map<Integer, ClanHallAuction> AUCTIONS = new HashMap<>();
	
	protected ClanHallAuctionManager()
	{
	}
	
	@ScheduleTarget
	private void onEventStart()
	{
		LOGGER.info(getClass().getSimpleName() + ": Clan Hall Auction has started!");
		AUCTIONS.clear();
		
		//@formatter:off
		ClanHallData.getInstance().getFreeAuctionableHall()
			.forEach(c -> AUCTIONS.put(c.getResidenceId(), new ClanHallAuction(c.getResidenceId())));
		//@formatter:on
	}
	
	@ScheduleTarget
	private void onEventEnd()
	{
		AUCTIONS.values().forEach(ClanHallAuction::finalizeAuctions);
		AUCTIONS.clear();
		LOGGER.info(getClass().getSimpleName() + ": Clan Hall Auction has ended!");
	}
	
	@Override
	public void onInitialized()
	{
	}
	
	public ClanHallAuction getClanHallAuctionById(int clanHallId)
	{
		return AUCTIONS.get(clanHallId);
	}
	
	public ClanHallAuction getClanHallAuctionByClan(Clan clan)
	{
		for (ClanHallAuction auction : AUCTIONS.values())
		{
			if (auction.getBids().containsKey(clan.getId()))
			{
				return auction;
			}
		}
		return null;
	}
	
	public boolean checkForClanBid(int clanHallId, Clan clan)
	{
		for (Entry<Integer, ClanHallAuction> auction : AUCTIONS.entrySet())
		{
			if ((auction.getKey() != clanHallId) && auction.getValue().getBids().containsKey(clan.getId()))
			{
				return true;
			}
		}
		return false;
	}
	
	public static ClanHallAuctionManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ClanHallAuctionManager INSTANCE = new ClanHallAuctionManager();
	}
}
