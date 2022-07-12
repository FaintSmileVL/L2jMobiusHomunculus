package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.model.stats.BaseStat;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * This server packet sends the player's henna information.
 * @author Zoey76
 */
public class HennaInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final List<Henna> _hennas = new ArrayList<>();
	
	public HennaInfo(PlayerInstance player)
	{
		_player = player;
		for (int i = 1; i < 4; i++)
		{
			if (player.getHenna(i) != null)
			{
				_hennas.add(player.getHenna(i));
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.HENNA_INFO.writeId(packet);
		
		packet.writeH(_player.getHennaValue(BaseStat.INT)); // equip INT
		packet.writeH(_player.getHennaValue(BaseStat.STR)); // equip STR
		packet.writeH(_player.getHennaValue(BaseStat.CON)); // equip CON
		packet.writeH(_player.getHennaValue(BaseStat.MEN)); // equip MEN
		packet.writeH(_player.getHennaValue(BaseStat.DEX)); // equip DEX
		packet.writeH(_player.getHennaValue(BaseStat.WIT)); // equip WIT
		packet.writeH(_player.getHennaValue(BaseStat.LUC)); // equip LUC
		packet.writeH(_player.getHennaValue(BaseStat.CHA)); // equip CHA
		packet.writeD(3 - _player.getHennaEmptySlots()); // Slots
		packet.writeD(_hennas.size()); // Size
		for (Henna henna : _hennas)
		{
			packet.writeD(henna.getDyeId());
			packet.writeD(henna.isAllowedClass(_player.getClassId()) ? 0x01 : 0x00);
		}
		
		final Henna premium = _player.getHenna(4);
		if (premium != null)
		{
			int duration = premium.getDuration();
			if (duration > 0)
			{
				final long currentTime = Chronos.currentTimeMillis();
				duration = (int) Math.max(0, _player.getVariables().getLong("HennaDuration4", currentTime) - currentTime) / 1000;
			}
			
			packet.writeD(premium.getDyeId());
			packet.writeD(duration); // Premium Slot Dye Time Left
			packet.writeD(premium.isAllowedClass(_player.getClassId()) ? 0x01 : 0x00);
		}
		else
		{
			packet.writeD(0x00); // Premium Slot Dye ID
			packet.writeD(0x00); // Premium Slot Dye Time Left
			packet.writeD(0x00); // Premium Slot Dye ID isValid
		}
		return true;
	}
}
