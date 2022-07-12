package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SetupGauge implements IClientOutgoingPacket
{
	public static final int BLUE = 0;
	public static final int RED = 1;
	public static final int CYAN = 2;
	
	private final int _dat1;
	private final int _time;
	private final int _time2;
	private final int _objectId;
	
	public SetupGauge(int objectId, int dat1, int time)
	{
		_objectId = objectId;
		_dat1 = dat1; // color 0-blue 1-red 2-cyan 3-green
		_time = time;
		_time2 = time;
	}
	
	public SetupGauge(int objectId, int color, int currentTime, int maxTime)
	{
		_objectId = objectId;
		_dat1 = color; // color 0-blue 1-red 2-cyan 3-green
		_time = currentTime;
		_time2 = maxTime;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SETUP_GAUGE.writeId(packet);
		packet.writeD(_objectId);
		packet.writeD(_dat1);
		packet.writeD(_time);
		packet.writeD(_time2);
		return true;
	}
}
