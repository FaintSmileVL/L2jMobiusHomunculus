package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.FortManager;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.siege.Fort;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExShowFortressInfo implements IClientOutgoingPacket
{
	public static final ExShowFortressInfo STATIC_PACKET = new ExShowFortressInfo();
	
	private ExShowFortressInfo()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_FORTRESS_INFO.writeId(packet);
		
		final Collection<Fort> forts = FortManager.getInstance().getForts();
		packet.writeD(forts.size());
		for (Fort fort : forts)
		{
			final Clan clan = fort.getOwnerClan();
			packet.writeD(fort.getResidenceId());
			packet.writeS(clan != null ? clan.getName() : "");
			packet.writeD(fort.getSiege().isInProgress() ? 0x01 : 0x00);
			// Time of possession
			packet.writeD(fort.getOwnedTime());
		}
		return true;
	}
}
