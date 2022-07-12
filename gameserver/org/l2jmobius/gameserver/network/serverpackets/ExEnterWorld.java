package org.l2jmobius.gameserver.network.serverpackets;

import java.time.Instant;
import java.time.ZoneId;
import java.time.zone.ZoneRules;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class ExEnterWorld implements IClientOutgoingPacket
{
	private final int _zoneIdOffsetSeconds;
	private final int _epochInSeconds;
	private final int _daylight;
	
	public ExEnterWorld()
	{
		Instant now = Instant.now();
		_epochInSeconds = (int) now.getEpochSecond();
		ZoneRules rules = ZoneId.systemDefault().getRules();
		_zoneIdOffsetSeconds = rules.getStandardOffset(now).getTotalSeconds();
		_daylight = (int) (rules.getDaylightSavings(now).toMillis() / 1000);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENTER_WORLD.writeId(packet);
		packet.writeD(_epochInSeconds);
		packet.writeD(-_zoneIdOffsetSeconds);
		packet.writeD(_daylight);
		packet.writeD(Config.SERVER_ID);
		return true;
	}
}