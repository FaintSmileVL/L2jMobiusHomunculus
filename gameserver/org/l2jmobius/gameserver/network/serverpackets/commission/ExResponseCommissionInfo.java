package org.l2jmobius.gameserver.network.serverpackets.commission;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author NosBit
 */
public class ExResponseCommissionInfo implements IClientOutgoingPacket
{
	public static final ExResponseCommissionInfo EMPTY = new ExResponseCommissionInfo();
	
	private final int _result;
	private final int _itemId;
	private final long _presetPricePerUnit;
	private final long _presetAmount;
	private final int _presetDurationType;
	
	private ExResponseCommissionInfo()
	{
		_result = 0;
		_itemId = 0;
		_presetPricePerUnit = 0;
		_presetAmount = 0;
		_presetDurationType = -1;
	}
	
	public ExResponseCommissionInfo(int itemId, long presetPricePerUnit, long presetAmount, int presetDurationType)
	{
		_result = 1;
		_itemId = itemId;
		_presetPricePerUnit = presetPricePerUnit;
		_presetAmount = presetAmount;
		_presetDurationType = presetDurationType;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_COMMISSION_INFO.writeId(packet);
		
		packet.writeD(_result);
		packet.writeD(_itemId);
		packet.writeQ(_presetPricePerUnit);
		packet.writeQ(_presetAmount);
		packet.writeD(_presetDurationType);
		return true;
	}
}
