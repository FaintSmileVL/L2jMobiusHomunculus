package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantFail implements IClientOutgoingPacket
{
	public static final ExEnchantFail STATIC_PACKET = new ExEnchantFail(0, 0);
	private final int _itemOne;
	private final int _itemTwo;
	
	public ExEnchantFail(int itemOne, int itemTwo)
	{
		_itemOne = itemOne;
		_itemTwo = itemTwo;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_FAIL.writeId(packet);
		
		packet.writeD(_itemOne);
		packet.writeD(_itemTwo);
		return true;
	}
}
