package org.l2jmobius.gameserver.network.serverpackets.pledgeV2;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.ClanMasteryData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.holders.ClanMasteryHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author Mobius
 */
public class ExPledgeMasteryInfo extends AbstractItemPacket
{
	final PlayerInstance _player;
	
	public ExPledgeMasteryInfo(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		final Clan clan = _player.getClan();
		if (clan == null)
		{
			return false;
		}
		
		OutgoingPackets.EX_PLEDGE_MASTERY_INFO.writeId(packet);
		
		packet.writeD(clan.getUsedDevelopmentPoints()); // Consumed development points
		packet.writeD(clan.getTotalDevelopmentPoints()); // Total development points
		packet.writeD(16); // Mastery count
		for (ClanMasteryHolder mastery : ClanMasteryData.getInstance().getMasteries())
		{
			if (mastery.getId() < 17)
			{
				final int id = mastery.getId();
				packet.writeD(id); // Mastery
				packet.writeD(0x00); // ?
				
				boolean available = true;
				if (clan.getLevel() < mastery.getClanLevel())
				{
					available = false;
				}
				else
				{
					final int previous = mastery.getPreviousMastery();
					final int previousAlt = mastery.getPreviousMasteryAlt();
					if (previousAlt > 0)
					{
						available = clan.hasMastery(previous) || clan.hasMastery(previousAlt);
					}
					else if (previous > 0)
					{
						available = clan.hasMastery(previous);
					}
				}
				
				packet.writeC(clan.hasMastery(id) ? 0x02 : available ? 0x01 : 0x00); // Availability.
			}
		}
		
		return true;
	}
}
