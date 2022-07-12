package org.l2jmobius.gameserver.network.serverpackets.appearance;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExPutShapeShiftingTargetItemResult implements IClientOutgoingPacket
{
	public static final int RESULT_FAILED = 0x00;
	public static final int RESULT_SUCCESS = 0x01;
	
	public static final ExPutShapeShiftingTargetItemResult FAILED = new ExPutShapeShiftingTargetItemResult(RESULT_FAILED, 0);
	
	private final int _resultId;
	private final long _price;
	
	public ExPutShapeShiftingTargetItemResult(int resultId, long price)
	{
		_resultId = resultId;
		_price = price;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_SHAPE_SHIFTING_TARGET_ITEM_RESULT.writeId(packet);
		
		packet.writeD(_resultId);
		packet.writeQ(_price);
		return true;
	}
}