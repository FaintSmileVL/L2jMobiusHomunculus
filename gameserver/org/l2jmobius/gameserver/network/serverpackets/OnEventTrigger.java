package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik, UnAfraid
 */
public class OnEventTrigger implements IClientOutgoingPacket
{
	private final int _emitterId;
	private final int _enabled;
	
	public OnEventTrigger(int emitterId, boolean enabled)
	{
		_emitterId = emitterId;
		_enabled = enabled ? 1 : 0;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EVENT_TRIGGER.writeId(packet);
		
		packet.writeD(_emitterId);
		packet.writeC(_enabled);
		return true;
	}
}