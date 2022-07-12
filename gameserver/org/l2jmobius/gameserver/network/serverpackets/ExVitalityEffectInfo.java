package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExVitalityEffectInfo implements IClientOutgoingPacket
{
	private final int _vitalityBonus;
	private final int _vitalityItemsRemaining;
	private final int _points;
	
	public ExVitalityEffectInfo(PlayerInstance player)
	{
		_points = player.getVitalityPoints();
		_vitalityBonus = (int) player.getStat().getVitalityExpBonus() * 100;
		_vitalityItemsRemaining = Config.VITALITY_MAX_ITEMS_ALLOWED - player.getVitalityItemsUsed();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_VITALITY_EFFECT_INFO.writeId(packet);
		
		packet.writeD(_points);
		packet.writeD(_vitalityBonus); // Vitality Bonus
		packet.writeH(0x00); // Vitality additional bonus in %
		packet.writeH(_vitalityItemsRemaining); // How much vitality items remaining for use
		packet.writeH(Config.VITALITY_MAX_ITEMS_ALLOWED); // Max number of items for use
		return true;
	}
}