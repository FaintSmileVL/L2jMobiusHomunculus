package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExPutIntensiveResultForVariationMake implements IClientOutgoingPacket
{
	private final int _refinerItemObjId;
	private final int _lifestoneItemId;
	private final int _gemstoneItemId;
	private final long _gemstoneCount;
	private final int _unk2;
	
	public ExPutIntensiveResultForVariationMake(int refinerItemObjId, int lifeStoneId, int gemstoneItemId, long gemstoneCount)
	{
		_refinerItemObjId = refinerItemObjId;
		_lifestoneItemId = lifeStoneId;
		_gemstoneItemId = gemstoneItemId;
		_gemstoneCount = gemstoneCount;
		_unk2 = 1;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_INTENSIVE_RESULT_FOR_VARIATION_MAKE.writeId(packet);
		
		packet.writeD(_refinerItemObjId);
		packet.writeD(_lifestoneItemId);
		packet.writeD(_gemstoneItemId);
		packet.writeQ(_gemstoneCount);
		packet.writeD(_unk2);
		return true;
	}
}
