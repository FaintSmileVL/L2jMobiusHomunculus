package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExPutCommissionResultForVariationMake implements IClientOutgoingPacket
{
	private final int _gemstoneObjId;
	private final int _itemId;
	private final long _gemstoneCount;
	private final int _unk1;
	private final int _unk2;
	
	public ExPutCommissionResultForVariationMake(int gemstoneObjId, long count, int itemId)
	{
		_gemstoneObjId = gemstoneObjId;
		_itemId = itemId;
		_gemstoneCount = count;
		_unk1 = 0;
		_unk2 = 1;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_COMMISSION_RESULT_FOR_VARIATION_MAKE.writeId(packet);
		
		packet.writeD(_gemstoneObjId);
		packet.writeD(_itemId);
		packet.writeQ(_gemstoneCount);
		packet.writeQ(_unk1);
		packet.writeD(_unk2);
		return true;
	}
}
