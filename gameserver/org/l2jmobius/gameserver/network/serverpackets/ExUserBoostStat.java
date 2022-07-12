package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.stats.Stat;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class ExUserBoostStat implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExUserBoostStat(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_USER_BOOST_STAT.writeId(packet);
		
		final int currentVitalityPoints = _player.getStat().getVitalityPoints();
		int vitalityBonus = 0;
		if (currentVitalityPoints > 105000)
		{
			vitalityBonus = 300;
		}
		else if (currentVitalityPoints > 70000)
		{
			vitalityBonus = 250;
		}
		else if (currentVitalityPoints > 35000)
		{
			vitalityBonus = 200;
		}
		else if (currentVitalityPoints > 0)
		{
			vitalityBonus = 150;
		}
		
		// final int bonus = (int) (_player.getStat().getExpBonusMultiplier() * 100);
		final int bonus = (int) (_player.getStat().getValue(Stat.BONUS_EXP, 0) + vitalityBonus);
		packet.writeC(bonus > 0 ? 2 : 0);
		packet.writeC(bonus > 0 ? 2 : 0);
		packet.writeH(bonus);
		
		return true;
	}
}
