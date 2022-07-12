package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExSendManorList implements IClientOutgoingPacket
{
	public static final ExSendManorList STATIC_PACKET = new ExSendManorList();
	
	private ExSendManorList()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SEND_MANOR_LIST.writeId(packet);
		
		final Collection<Castle> castles = CastleManager.getInstance().getCastles();
		packet.writeD(castles.size());
		for (Castle castle : castles)
		{
			packet.writeD(castle.getResidenceId());
		}
		return true;
	}
}
