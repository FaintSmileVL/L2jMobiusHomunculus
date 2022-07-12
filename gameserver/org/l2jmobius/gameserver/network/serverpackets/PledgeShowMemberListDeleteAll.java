package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PledgeShowMemberListDeleteAll implements IClientOutgoingPacket
{
	public static final PledgeShowMemberListDeleteAll STATIC_PACKET = new PledgeShowMemberListDeleteAll();
	
	private PledgeShowMemberListDeleteAll()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLEDGE_SHOW_MEMBER_LIST_DELETE_ALL.writeId(packet);
		return true;
	}
}
