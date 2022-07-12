package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.ClanTable;
import org.l2jmobius.gameserver.enums.TaxType;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExShowCastleInfo implements IClientOutgoingPacket
{
	public static final ExShowCastleInfo STATIC_PACKET = new ExShowCastleInfo();
	
	private ExShowCastleInfo()
	{
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_CASTLE_INFO.writeId(packet);
		
		final Collection<Castle> castles = CastleManager.getInstance().getCastles();
		packet.writeD(castles.size());
		for (Castle castle : castles)
		{
			packet.writeD(castle.getResidenceId());
			if (castle.getOwnerId() > 0)
			{
				if (ClanTable.getInstance().getClan(castle.getOwnerId()) != null)
				{
					packet.writeS(ClanTable.getInstance().getClan(castle.getOwnerId()).getName());
				}
				else
				{
					LOGGER.warning("Castle owner with no name! Castle: " + castle.getName() + " has an OwnerId = " + castle.getOwnerId() + " who does not have a  name!");
					packet.writeS("");
				}
			}
			else
			{
				packet.writeS("");
			}
			packet.writeD(castle.getTaxPercent(TaxType.BUY));
			packet.writeD((int) (castle.getSiege().getSiegeDate().getTimeInMillis() / 1000));
			
			packet.writeC(castle.getSiege().isInProgress() ? 0x01 : 0x00); // Grand Crusade
			packet.writeC(castle.getSide().ordinal()); // Grand Crusade
		}
		return true;
	}
}
