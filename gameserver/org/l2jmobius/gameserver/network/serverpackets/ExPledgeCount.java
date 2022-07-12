package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExPledgeCount implements IClientOutgoingPacket
{
	private final int _count;
	
	public ExPledgeCount(Clan clan)
	{
		_count = clan.getOnlineMembersCount();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_COUNT.writeId(packet);
		
		packet.writeD(_count);
		return true;
	}
}
