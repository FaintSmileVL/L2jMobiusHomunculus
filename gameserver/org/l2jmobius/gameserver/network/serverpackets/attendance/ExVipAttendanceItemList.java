package org.l2jmobius.gameserver.network.serverpackets.attendance;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.AttendanceRewardData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.AttendanceInfoHolder;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExVipAttendanceItemList implements IClientOutgoingPacket
{
	boolean _available;
	int _index;
	
	public ExVipAttendanceItemList(PlayerInstance player)
	{
		final AttendanceInfoHolder attendanceInfo = player.getAttendanceInfo();
		_available = attendanceInfo.isRewardAvailable();
		_index = attendanceInfo.getRewardIndex();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_VIP_ATTENDANCE_ITEM_LIST.writeId(packet);
		packet.writeC(_available ? _index + 1 : _index); // index to receive?
		packet.writeC(_index); // last received index?
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeC(0x01);
		packet.writeC(_available ? 0x01 : 0x00); // player can receive reward today?
		packet.writeC(250);
		packet.writeC(AttendanceRewardData.getInstance().getRewardsCount()); // reward size
		int rewardCounter = 0;
		for (ItemHolder reward : AttendanceRewardData.getInstance().getRewards())
		{
			rewardCounter++;
			packet.writeD(reward.getId());
			packet.writeQ(reward.getCount());
			packet.writeC(0x01); // is unknown?
			packet.writeC((rewardCounter % 7) == 0 ? 0x01 : 0x00); // is last in row?
		}
		packet.writeC(0x00);
		packet.writeD(0x00);
		return true;
	}
}
