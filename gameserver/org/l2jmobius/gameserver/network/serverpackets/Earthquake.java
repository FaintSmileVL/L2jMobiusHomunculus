package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.interfaces.ILocational;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class Earthquake implements IClientOutgoingPacket
{
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _intensity;
	private final int _duration;
	
	/**
	 * @param location
	 * @param intensity
	 * @param duration
	 */
	public Earthquake(ILocational location, int intensity, int duration)
	{
		_x = location.getX();
		_y = location.getY();
		_z = location.getZ();
		_intensity = intensity;
		_duration = duration;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param intensity
	 * @param duration
	 */
	public Earthquake(int x, int y, int z, int intensity, int duration)
	{
		_x = x;
		_y = y;
		_z = z;
		_intensity = intensity;
		_duration = duration;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EARTHQUAKE.writeId(packet);
		
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		packet.writeD(_intensity);
		packet.writeD(_duration);
		packet.writeD(0x00); // Unknown
		return true;
	}
}
