package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class Snoop implements IClientOutgoingPacket
{
	private final int _convoId;
	private final String _name;
	private final ChatType _type;
	private final String _speaker;
	private final String _msg;
	
	public Snoop(int id, String name, ChatType type, String speaker, String msg)
	{
		_convoId = id;
		_name = name;
		_type = type;
		_speaker = speaker;
		_msg = msg;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SNOOP.writeId(packet);
		
		packet.writeD(_convoId);
		packet.writeS(_name);
		packet.writeD(0x00); // ??
		packet.writeD(_type.getClientId());
		packet.writeS(_speaker);
		packet.writeS(_msg);
		return true;
	}
}