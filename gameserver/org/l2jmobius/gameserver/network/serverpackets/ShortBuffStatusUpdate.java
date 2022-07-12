package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShortBuffStatusUpdate implements IClientOutgoingPacket
{
	public static final ShortBuffStatusUpdate RESET_SHORT_BUFF = new ShortBuffStatusUpdate(0, 0, 0, 0);
	
	private final int _skillId;
	private final int _skillLevel;
	private final int _skillSubLevel;
	private final int _duration;
	
	public ShortBuffStatusUpdate(int skillId, int skillLevel, int skillSubLevel, int duration)
	{
		_skillId = skillId;
		_skillLevel = skillLevel;
		_skillSubLevel = skillSubLevel;
		_duration = duration;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHORT_BUFF_STATUS_UPDATE.writeId(packet);
		
		packet.writeD(_skillId);
		packet.writeH(_skillLevel);
		packet.writeH(_skillSubLevel);
		packet.writeD(_duration);
		return true;
	}
}
