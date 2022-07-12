package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author janiii
 */
public class ExEventMatchMessage implements IClientOutgoingPacket
{
	private final int _type;
	private final String _message;
	
	/**
	 * Create an event match message.
	 * @param type 0 - gm, 1 - finish, 2 - start, 3 - game over, 4 - 1, 5 - 2, 6 - 3, 7 - 4, 8 - 5
	 * @param message message to show, only when type is 0 - gm
	 */
	public ExEventMatchMessage(int type, String message)
	{
		_type = type;
		_message = message;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_EVENT_MATCH_MESSAGE.writeId(packet);
		
		packet.writeC(_type);
		packet.writeS(_message);
		return true;
	}
}
