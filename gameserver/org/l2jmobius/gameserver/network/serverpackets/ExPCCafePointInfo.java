package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 * @author UnAfraid
 */
public class ExPCCafePointInfo implements IClientOutgoingPacket
{
	private final int _points;
	private final int _mAddPoint;
	private final int _mPeriodType;
	private final int _remainTime;
	private final int _pointType;
	private final int _time;
	
	public ExPCCafePointInfo()
	{
		_points = 0;
		_mAddPoint = 0;
		_remainTime = 0;
		_mPeriodType = 0;
		_pointType = 0;
		_time = 0;
	}
	
	public ExPCCafePointInfo(int points, int pointsToAdd, int time)
	{
		_points = points;
		_mAddPoint = pointsToAdd;
		_mPeriodType = 1;
		_remainTime = 0; // No idea why but retail sends 42..
		_pointType = pointsToAdd < 0 ? 2 : 1; // When using points is 3
		_time = time;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PCCAFE_POINT_INFO.writeId(packet);
		
		packet.writeD(_points); // num points
		packet.writeD(_mAddPoint); // points inc display
		packet.writeC(_mPeriodType); // period(0=don't show window,1=acquisition,2=use points)
		packet.writeD(_remainTime); // period hours left
		packet.writeC(_pointType); // points inc display color(0=yellow, 1=cyan-blue, 2=red, all other black)
		packet.writeD(_time * 3); // value is in seconds * 3
		return true;
	}
}
