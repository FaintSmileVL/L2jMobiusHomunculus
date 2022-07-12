package org.l2jmobius.gameserver.network.serverpackets.appearance;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExShapeShiftingResult implements IClientOutgoingPacket
{
	public static final int RESULT_FAILED = 0x00;
	public static final int RESULT_SUCCESS = 0x01;
	public static final int RESULT_CLOSE = 0x02;
	
	public static final ExShapeShiftingResult FAILED = new ExShapeShiftingResult(RESULT_FAILED, 0, 0);
	public static final ExShapeShiftingResult CLOSE = new ExShapeShiftingResult(RESULT_CLOSE, 0, 0);
	
	private final int _result;
	private final int _targetItemId;
	private final int _extractItemId;
	
	public ExShapeShiftingResult(int result, int targetItemId, int extractItemId)
	{
		_result = result;
		_targetItemId = targetItemId;
		_extractItemId = extractItemId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHAPE_SHIFTING_RESULT.writeId(packet);
		
		packet.writeD(_result);
		packet.writeD(_targetItemId);
		packet.writeD(_extractItemId);
		return true;
	}
}
