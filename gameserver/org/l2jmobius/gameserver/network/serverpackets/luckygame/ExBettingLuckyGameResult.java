package org.l2jmobius.gameserver.network.serverpackets.luckygame;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.LuckyGameItemType;
import org.l2jmobius.gameserver.enums.LuckyGameResultType;
import org.l2jmobius.gameserver.enums.LuckyGameType;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExBettingLuckyGameResult implements IClientOutgoingPacket
{
	public static final ExBettingLuckyGameResult NORMAL_INVALID_ITEM_COUNT = new ExBettingLuckyGameResult(LuckyGameResultType.INVALID_ITEM_COUNT, LuckyGameType.NORMAL);
	public static final ExBettingLuckyGameResult LUXURY_INVALID_ITEM_COUNT = new ExBettingLuckyGameResult(LuckyGameResultType.INVALID_ITEM_COUNT, LuckyGameType.LUXURY);
	public static final ExBettingLuckyGameResult NORMAL_INVALID_CAPACITY = new ExBettingLuckyGameResult(LuckyGameResultType.INVALID_CAPACITY, LuckyGameType.NORMAL);
	public static final ExBettingLuckyGameResult LUXURY_INVALID_CAPACITY = new ExBettingLuckyGameResult(LuckyGameResultType.INVALID_CAPACITY, LuckyGameType.LUXURY);
	
	private final LuckyGameResultType _result;
	private final LuckyGameType _type;
	private final Map<LuckyGameItemType, List<ItemHolder>> _rewards;
	private final int _ticketCount;
	private final int _size;
	
	public ExBettingLuckyGameResult(LuckyGameResultType result, LuckyGameType type)
	{
		_result = result;
		_type = type;
		_rewards = new EnumMap<>(LuckyGameItemType.class);
		_ticketCount = 0;
		_size = 0;
	}
	
	public ExBettingLuckyGameResult(LuckyGameResultType result, LuckyGameType type, Map<LuckyGameItemType, List<ItemHolder>> rewards, int ticketCount)
	{
		_result = result;
		_type = type;
		_rewards = rewards;
		_ticketCount = ticketCount;
		_size = (int) rewards.values().stream().mapToLong(i -> i.stream().count()).sum();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_BETTING_LUCKY_GAME_RESULT.writeId(packet);
		packet.writeD(_result.getClientId());
		packet.writeD(_type.ordinal());
		packet.writeD(_ticketCount);
		packet.writeD(_size);
		for (Entry<LuckyGameItemType, List<ItemHolder>> reward : _rewards.entrySet())
		{
			for (ItemHolder item : reward.getValue())
			{
				packet.writeD(reward.getKey().getClientId());
				packet.writeD(item.getId());
				packet.writeD((int) item.getCount());
			}
		}
		return true;
	}
}
