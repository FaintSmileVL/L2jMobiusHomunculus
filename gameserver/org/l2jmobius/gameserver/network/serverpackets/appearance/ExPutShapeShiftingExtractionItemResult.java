package org.l2jmobius.gameserver.network.serverpackets.appearance;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExPutShapeShiftingExtractionItemResult implements IClientOutgoingPacket
{
	public static final ExPutShapeShiftingExtractionItemResult FAILED = new ExPutShapeShiftingExtractionItemResult(0x00);
	public static final ExPutShapeShiftingExtractionItemResult SUCCESS = new ExPutShapeShiftingExtractionItemResult(0x01);
	
	private final int _result;
	
	public ExPutShapeShiftingExtractionItemResult(int result)
	{
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PUT_SHAPE_SHIFTING_EXTRACTION_ITEM_RESULT.writeId(packet);
		
		packet.writeD(_result);
		return true;
	}
}