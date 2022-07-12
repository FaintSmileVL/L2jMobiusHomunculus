package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Objects;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.CommandChannel;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author chris_00
 */
public class ExMultiPartyCommandChannelInfo implements IClientOutgoingPacket
{
	private final CommandChannel _channel;
	
	public ExMultiPartyCommandChannelInfo(CommandChannel channel)
	{
		Objects.requireNonNull(channel);
		_channel = channel;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MULTI_PARTY_COMMAND_CHANNEL_INFO.writeId(packet);
		
		packet.writeS(_channel.getLeader().getName());
		packet.writeD(0x00); // Channel loot 0 or 1
		packet.writeD(_channel.getMemberCount());
		
		packet.writeD(_channel.getParties().size());
		for (Party p : _channel.getParties())
		{
			packet.writeS(p.getLeader().getName());
			packet.writeD(p.getLeaderObjectId());
			packet.writeD(p.getMemberCount());
		}
		return true;
	}
}
