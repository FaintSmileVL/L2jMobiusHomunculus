package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CursedWeaponsManager;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-
 */
public class ExCursedWeaponList implements IClientOutgoingPacket
{
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CURSED_WEAPON_LIST.writeId(packet);
		
		final Set<Integer> ids = CursedWeaponsManager.getInstance().getCursedWeaponsIds();
		packet.writeD(ids.size());
		ids.forEach(packet::writeD);
		return true;
	}
}
