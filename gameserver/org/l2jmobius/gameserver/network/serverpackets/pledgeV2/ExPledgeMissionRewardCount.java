package org.l2jmobius.gameserver.network.serverpackets.pledgeV2;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.DailyMissionData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Bonux (bonuxq@gmail.com)
 * @date 29.09.2019
 **/
public class ExPledgeMissionRewardCount implements IClientOutgoingPacket
{
	private final int _doneMissionsCount;
	private final int _availableMissionsCount;
	
	public ExPledgeMissionRewardCount(PlayerInstance player)
	{
		_doneMissionsCount = (int) DailyMissionData.getInstance().getDailyMissionData(player).stream().filter(d -> d.getRecentlyCompleted(player)).count();
		_availableMissionsCount = (int) DailyMissionData.getInstance().getDailyMissionData(player).stream().filter(d -> d.getStatus(player) == 1).count();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_MISSION_REWARD_COUNT.writeId(packet);
		
		packet.writeD(Math.min(_availableMissionsCount, _doneMissionsCount)); // Received missions rewards.
		packet.writeD(_availableMissionsCount); // Available missions rewards. 18 - for noble, 20 - for honnorable noble.
		
		return true;
	}
}
