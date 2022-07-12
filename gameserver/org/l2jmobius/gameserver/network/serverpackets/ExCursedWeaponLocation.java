package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Format: (ch) d[ddddd]
 * @author -Wooden-
 */
public class ExCursedWeaponLocation implements IClientOutgoingPacket
{
	private final List<CursedWeaponInfo> _cursedWeaponInfo;
	
	public ExCursedWeaponLocation(List<CursedWeaponInfo> cursedWeaponInfo)
	{
		_cursedWeaponInfo = cursedWeaponInfo;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CURSED_WEAPON_LOCATION.writeId(packet);
		
		if (!_cursedWeaponInfo.isEmpty())
		{
			packet.writeD(_cursedWeaponInfo.size());
			for (CursedWeaponInfo w : _cursedWeaponInfo)
			{
				packet.writeD(w.id);
				packet.writeD(w.activated);
				
				packet.writeD(w.pos.getX());
				packet.writeD(w.pos.getY());
				packet.writeD(w.pos.getZ());
			}
		}
		else
		{
			packet.writeD(0);
		}
		return true;
	}
	
	public static class CursedWeaponInfo
	{
		public Location pos;
		public int id;
		public int activated; // 0 - not activated ? 1 - activated
		
		public CursedWeaponInfo(Location p, int cwId, int status)
		{
			pos = p;
			id = cwId;
			activated = status;
		}
	}
}
