package org.l2jmobius.gameserver.network.serverpackets.attributechange;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExChangeAttributeFail implements IClientOutgoingPacket
{
	public static final IClientOutgoingPacket STATIC = new ExChangeAttributeFail();
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_ATTRIBUTE_FAIL.writeId(packet);
		return true;
	}
}