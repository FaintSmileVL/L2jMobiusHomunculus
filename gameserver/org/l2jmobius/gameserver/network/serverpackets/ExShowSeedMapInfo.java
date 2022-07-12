package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.GraciaSeedsManager;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExShowSeedMapInfo implements IClientOutgoingPacket
{
	public static final ExShowSeedMapInfo STATIC_PACKET = new ExShowSeedMapInfo();
	
	private ExShowSeedMapInfo()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_SEED_MAP_INFO.writeId(packet);
		
		packet.writeD(2); // seed count
		
		// Seed of Destruction
		packet.writeD(1); // id 1? Grand Crusade
		packet.writeD(2770 + GraciaSeedsManager.getInstance().getSoDState()); // sys msg id
		
		// Seed of Infinity
		packet.writeD(2); // id 2? Grand Crusade
		// Manager not implemented yet
		packet.writeD(2766); // sys msg id
		return true;
	}
}
