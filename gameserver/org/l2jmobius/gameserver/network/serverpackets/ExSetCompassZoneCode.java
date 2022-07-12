package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExSetCompassZoneCode implements IClientOutgoingPacket
{
	// TODO: Enum
	public static final int ALTEREDZONE = 0x08;
	public static final int SIEGEWARZONE1 = 0x0A;
	public static final int SIEGEWARZONE2 = 0x0B;
	public static final int PEACEZONE = 0x0C;
	public static final int SEVENSIGNSZONE = 0x0D;
	public static final int PVPZONE = 0x0E;
	public static final int GENERALZONE = 0x0F;
	// TODO: need to find the desired value
	public static final int NOPVPZONE = 0x0C;
	
	private final int _zoneType;
	
	public ExSetCompassZoneCode(int value)
	{
		_zoneType = value;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SET_COMPASS_ZONE_CODE.writeId(packet);
		
		packet.writeD(_zoneType);
		return true;
	}
}
