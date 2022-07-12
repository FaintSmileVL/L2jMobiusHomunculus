package org.l2jmobius.gameserver.network.serverpackets.huntingzones;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.data.xml.TimedHuntingZoneData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.TimedHuntingZoneHolder;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class TimedHuntingZoneList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final boolean _isInTimedHuntingZone;
	
	public TimedHuntingZoneList(PlayerInstance player)
	{
		_player = player;
		_isInTimedHuntingZone = player.isInsideZone(ZoneId.TIMED_HUNTING);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TIME_RESTRICT_FIELD_LIST.writeId(packet);
		int remainingTime;
		final long currentTime = Chronos.currentTimeMillis();
		packet.writeD(TimedHuntingZoneData.getInstance().getSize()); // zone count
		for (TimedHuntingZoneHolder holder : TimedHuntingZoneData.getInstance().getAllHuntingZones())
		{
			packet.writeD(holder.getEntryFee() == 0 ? 0 : 1); // required item count
			packet.writeD(holder.getEntryItemId());
			packet.writeQ(holder.getEntryFee());
			packet.writeD(holder.isWeekly() ? 0 : 1); // reset cycle
			packet.writeD(holder.getZoneId());
			packet.writeD(holder.getMinLevel());
			packet.writeD(holder.getMaxLevel());
			packet.writeD(holder.getInitialTime() / 1000); // remain time base
			remainingTime = _player.getTimedHuntingZoneRemainingTime(holder.getZoneId());
			if ((remainingTime == 0) && ((_player.getTimedHuntingZoneInitialEntry(holder.getZoneId()) + holder.getResetDelay()) < currentTime))
			{
				remainingTime = holder.getInitialTime();
			}
			packet.writeD(remainingTime / 1000); // remain time
			packet.writeD(holder.getMaximumAddedTime() / 1000);
			packet.writeD(holder.getRemainRefillTime());
			packet.writeD(holder.getRefillTimeMax());
			packet.writeD(_isInTimedHuntingZone ? 0 : 1); // field activated (272 C to D)
			packet.writeH(0); // 245
		}
		return true;
	}
}