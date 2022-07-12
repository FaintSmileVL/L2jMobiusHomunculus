package org.l2jmobius.gameserver.network.clientpackets.attendance;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.data.xml.AttendanceRewardData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.AttendanceInfoHolder;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;
import org.l2jmobius.gameserver.network.serverpackets.attendance.ExConfirmVipAttendanceCheck;

/**
 * @author Mobius
 */
public class RequestVipAttendanceCheck implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (!Config.ENABLE_ATTENDANCE_REWARDS)
		{
			player.sendPacket(SystemMessageId.DUE_TO_A_SYSTEM_ERROR_THE_ATTENDANCE_REWARD_CANNOT_BE_RECEIVED_PLEASE_TRY_AGAIN_LATER_BY_GOING_TO_MENU_ATTENDANCE_CHECK);
			return;
		}
		
		if (Config.PREMIUM_ONLY_ATTENDANCE_REWARDS && !player.hasPremiumStatus())
		{
			player.sendPacket(SystemMessageId.YOUR_VIP_RANK_IS_TOO_LOW_TO_RECEIVE_THE_REWARD);
			return;
		}
		
		// Check login delay.
		if (player.getUptime() < (Config.ATTENDANCE_REWARD_DELAY * 60 * 1000))
		{
			// player.sendPacket(SystemMessageId.YOU_DO_NOT_MEET_THE_LEVEL_REQUIREMENTS_TO_RECEIVE_THE_ATTENDANCE_REWARD_PLEASE_CHECK_THE_REQUIRED_LEVEL_YOU_CAN_REDEEM_YOUR_REWARD_30_MINUTES_AFTER_LOGGING_IN);
			player.sendMessage("You can redeem your reward " + Config.ATTENDANCE_REWARD_DELAY + " minutes after logging in.");
			return;
		}
		
		final AttendanceInfoHolder attendanceInfo = player.getAttendanceInfo();
		final boolean isRewardAvailable = attendanceInfo.isRewardAvailable();
		final int rewardIndex = attendanceInfo.getRewardIndex();
		final ItemHolder reward = AttendanceRewardData.getInstance().getRewards().get(rewardIndex);
		final Item itemTemplate = ItemTable.getInstance().getTemplate(reward.getId());
		
		// Weight check.
		final long weight = itemTemplate.getWeight() * reward.getCount();
		final long slots = itemTemplate.isStackable() ? 1 : reward.getCount();
		if (!player.getInventory().validateWeight(weight) || !player.getInventory().validateCapacity(slots))
		{
			player.sendPacket(SystemMessageId.THE_ATTENDANCE_REWARD_CANNOT_BE_RECEIVED_BECAUSE_THE_INVENTORY_WEIGHT_QUANTITY_LIMIT_HAS_BEEN_EXCEEDED);
			return;
		}
		
		// Reward.
		if (isRewardAvailable)
		{
			// Save date and index.
			player.setAttendanceInfo(rewardIndex + 1);
			// Add items to player.
			player.addItem("Attendance Reward", reward, player, true);
			// Send message.
			final SystemMessage msg = new SystemMessage(SystemMessageId.YOU_VE_RECEIVED_YOUR_VIP_ATTENDANCE_REWARD_FOR_DAY_S1);
			msg.addInt(rewardIndex + 1);
			player.sendPacket(msg);
			// Send confirm packet.
			player.sendPacket(new ExConfirmVipAttendanceCheck(isRewardAvailable, rewardIndex + 1));
		}
	}
}
