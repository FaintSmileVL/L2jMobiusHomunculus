package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Lets drink to code!
 * @author zabbix
 */
public class GameGuardQuery implements IClientOutgoingPacket
{
	public static final GameGuardQuery STATIC_PACKET = new GameGuardQuery();
	
	private GameGuardQuery()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.GAME_GUARD_QUERY.writeId(packet);
		
		packet.writeD(0x27533DD9);
		packet.writeD(0x2E72A51D);
		packet.writeD(0x2017038B);
		packet.writeD(0xC35B1EA3);
		return true;
	}
}
