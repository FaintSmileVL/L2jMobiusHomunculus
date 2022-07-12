package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class KeyPacket implements IClientOutgoingPacket
{
	private final byte[] _key;
	private final int _result;
	
	public KeyPacket(byte[] key, int result)
	{
		_key = key;
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.VERSION_CHECK.writeId(packet);
		
		packet.writeC(_result); // 0 - wrong protocol, 1 - protocol ok
		for (int i = 0; i < 8; i++)
		{
			packet.writeC(_key[i]); // key
		}
		packet.writeD(0x01);
		packet.writeD(Config.SERVER_ID); // server id
		packet.writeC(0x01);
		packet.writeD(0x00); // obfuscation key
		packet.writeC((Config.SERVER_LIST_TYPE & 0x400) == 0x400 ? 0x01 : 0x00); // isClassic
		return true;
	}
}
