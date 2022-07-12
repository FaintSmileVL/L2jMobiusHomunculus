package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ManagePledgePower implements IClientOutgoingPacket
{
	private final int _action;
	private final Clan _clan;
	private final int _rank;
	
	public ManagePledgePower(Clan clan, int action, int rank)
	{
		_clan = clan;
		_action = action;
		_rank = rank;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MANAGE_PLEDGE_POWER.writeId(packet);
		
		packet.writeD(_rank);
		packet.writeD(_action);
		packet.writeD(_clan.getRankPrivs(_rank).getBitmask());
		
		return true;
	}
}
