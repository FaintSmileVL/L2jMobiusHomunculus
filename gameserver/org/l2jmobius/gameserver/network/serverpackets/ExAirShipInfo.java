package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.AirShipInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExAirShipInfo implements IClientOutgoingPacket
{
	// store some parameters, because they can be changed during broadcast
	private final AirShipInstance _ship;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _heading;
	private final int _moveSpeed;
	private final int _rotationSpeed;
	private final int _captain;
	private final int _helm;
	
	public ExAirShipInfo(AirShipInstance ship)
	{
		_ship = ship;
		_x = ship.getX();
		_y = ship.getY();
		_z = ship.getZ();
		_heading = ship.getHeading();
		_moveSpeed = (int) ship.getStat().getMoveSpeed();
		_rotationSpeed = (int) ship.getStat().getRotationSpeed();
		_captain = ship.getCaptainId();
		_helm = ship.getHelmObjectId();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_AIR_SHIP_INFO.writeId(packet);
		
		packet.writeD(_ship.getObjectId());
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		packet.writeD(_heading);
		
		packet.writeD(_captain);
		packet.writeD(_moveSpeed);
		packet.writeD(_rotationSpeed);
		packet.writeD(_helm);
		if (_helm != 0)
		{
			// TODO: unhardcode these!
			packet.writeD(0x16e); // Controller X
			packet.writeD(0x00); // Controller Y
			packet.writeD(0x6b); // Controller Z
			packet.writeD(0x15c); // Captain X
			packet.writeD(0x00); // Captain Y
			packet.writeD(0x69); // Captain Z
		}
		else
		{
			packet.writeD(0x00);
			packet.writeD(0x00);
			packet.writeD(0x00);
			packet.writeD(0x00);
			packet.writeD(0x00);
			packet.writeD(0x00);
		}
		
		packet.writeD(_ship.getFuel());
		packet.writeD(_ship.getMaxFuel());
		return true;
	}
}
