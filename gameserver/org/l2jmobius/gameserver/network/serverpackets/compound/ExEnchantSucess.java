package org.l2jmobius.gameserver.network.serverpackets.compound;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExEnchantSucess implements IClientOutgoingPacket
{
	private final int _itemId;
	
	public ExEnchantSucess(int itemId)
	{
		_itemId = itemId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_SUCESS.writeId(packet);
		
		packet.writeD(_itemId);
		return true;
	}
}
