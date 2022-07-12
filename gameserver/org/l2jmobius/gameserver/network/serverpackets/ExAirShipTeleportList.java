package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.VehiclePathPoint;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ExAirShipTeleportList implements IClientOutgoingPacket
{
	private final int _dockId;
	private final VehiclePathPoint[][] _teleports;
	private final int[] _fuelConsumption;
	
	public ExAirShipTeleportList(int dockId, VehiclePathPoint[][] teleports, int[] fuelConsumption)
	{
		_dockId = dockId;
		_teleports = teleports;
		_fuelConsumption = fuelConsumption;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_AIR_SHIP_TELEPORT_LIST.writeId(packet);
		
		packet.writeD(_dockId);
		if (_teleports != null)
		{
			packet.writeD(_teleports.length);
			
			for (int i = 0; i < _teleports.length; i++)
			{
				packet.writeD(i - 1);
				packet.writeD(_fuelConsumption[i]);
				final VehiclePathPoint[] path = _teleports[i];
				final VehiclePathPoint dst = path[path.length - 1];
				packet.writeD(dst.getX());
				packet.writeD(dst.getY());
				packet.writeD(dst.getZ());
			}
		}
		else
		{
			packet.writeD(0);
		}
		return true;
	}
}
