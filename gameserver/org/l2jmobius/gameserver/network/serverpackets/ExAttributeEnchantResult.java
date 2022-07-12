package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.AttributeType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExAttributeEnchantResult implements IClientOutgoingPacket
{
	private final int _result;
	private final int _isWeapon;
	private final int _type;
	private final int _before;
	private final int _after;
	private final int _successCount;
	private final int _failedCount;
	
	public ExAttributeEnchantResult(int result, boolean isWeapon, AttributeType type, int before, int after, int successCount, int failedCount)
	{
		_result = result;
		_isWeapon = isWeapon ? 1 : 0;
		_type = type.getClientId();
		_before = before;
		_after = after;
		_successCount = successCount;
		_failedCount = failedCount;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ATTRIBUTE_ENCHANT_RESULT.writeId(packet);
		
		packet.writeD(_result);
		packet.writeC(_isWeapon);
		packet.writeH(_type);
		packet.writeH(_before);
		packet.writeH(_after);
		packet.writeH(_successCount);
		packet.writeH(_failedCount);
		return true;
	}
}
