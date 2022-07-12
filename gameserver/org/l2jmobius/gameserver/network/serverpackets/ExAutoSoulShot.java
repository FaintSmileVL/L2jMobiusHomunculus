package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExAutoSoulShot implements IClientOutgoingPacket
{
	private final int _itemId;
	private final boolean _enable;
	private final int _type;
	
	/**
	 * @param itemId
	 * @param enable
	 * @param type
	 */
	public ExAutoSoulShot(int itemId, boolean enable, int type)
	{
		_itemId = itemId;
		_enable = enable;
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_AUTO_SOUL_SHOT.writeId(packet);
		
		packet.writeD(_itemId);
		packet.writeD(_enable ? 0x01 : 0x00);
		packet.writeD(_type);
		return true;
	}
}
