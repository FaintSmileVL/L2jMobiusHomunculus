package org.l2jmobius.gameserver.model.matching;

import org.l2jmobius.gameserver.enums.ExManagePartyRoomMemberType;
import org.l2jmobius.gameserver.enums.MatchingMemberType;
import org.l2jmobius.gameserver.enums.MatchingRoomType;
import org.l2jmobius.gameserver.enums.UserInfoType;
import org.l2jmobius.gameserver.instancemanager.MatchingRoomManager;
import org.l2jmobius.gameserver.model.CommandChannel;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ExDissmissMPCCRoom;
import org.l2jmobius.gameserver.network.serverpackets.ExMPCCRoomInfo;
import org.l2jmobius.gameserver.network.serverpackets.ExMPCCRoomMember;
import org.l2jmobius.gameserver.network.serverpackets.ExManageMpccRoomMember;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Sdw
 */
public class CommandChannelMatchingRoom extends MatchingRoom
{
	public CommandChannelMatchingRoom(String title, int loot, int minLevel, int maxLevel, int maxmem, PlayerInstance leader)
	{
		super(title, loot, minLevel, maxLevel, maxmem, leader);
	}
	
	@Override
	protected void onRoomCreation(PlayerInstance player)
	{
		player.sendPacket(SystemMessageId.THE_COMMAND_CHANNEL_MATCHING_ROOM_WAS_CREATED);
	}
	
	@Override
	protected void notifyInvalidCondition(PlayerInstance player)
	{
		player.sendPacket(SystemMessageId.YOU_CANNOT_ENTER_THE_COMMAND_CHANNEL_MATCHING_ROOM_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
	}
	
	@Override
	protected void notifyNewMember(PlayerInstance player)
	{
		// Update other players
		for (PlayerInstance member : getMembers())
		{
			if (member != player)
			{
				member.sendPacket(new ExManageMpccRoomMember(member, this, ExManagePartyRoomMemberType.ADD_MEMBER));
			}
		}
		
		// Send SystemMessage to other players
		final SystemMessage sm = new SystemMessage(SystemMessageId.C1_ENTERED_THE_COMMAND_CHANNEL_MATCHING_ROOM);
		sm.addPcName(player);
		for (PlayerInstance member : getMembers())
		{
			if (member != player)
			{
				sm.sendTo(member);
			}
		}
		
		// Update new player
		player.sendPacket(new ExMPCCRoomInfo(this));
		player.sendPacket(new ExMPCCRoomMember(player, this));
	}
	
	@Override
	protected void notifyRemovedMember(PlayerInstance player, boolean kicked, boolean leaderChanged)
	{
		getMembers().forEach(p ->
		{
			p.sendPacket(new ExMPCCRoomInfo(this));
			p.sendPacket(new ExMPCCRoomMember(player, this));
		});
		
		player.sendPacket(new SystemMessage(kicked ? SystemMessageId.YOU_WERE_EXPELLED_FROM_THE_COMMAND_CHANNEL_MATCHING_ROOM : SystemMessageId.YOU_EXITED_FROM_THE_COMMAND_CHANNEL_MATCHING_ROOM));
	}
	
	@Override
	public void disbandRoom()
	{
		getMembers().forEach(p ->
		{
			p.sendPacket(SystemMessageId.THE_COMMAND_CHANNEL_MATCHING_ROOM_WAS_CANCELLED);
			p.sendPacket(ExDissmissMPCCRoom.STATIC_PACKET);
			p.setMatchingRoom(null);
			p.broadcastUserInfo(UserInfoType.CLAN);
			MatchingRoomManager.getInstance().addToWaitingList(p);
		});
		
		getMembers().clear();
		
		MatchingRoomManager.getInstance().removeMatchingRoom(this);
	}
	
	@Override
	public MatchingRoomType getRoomType()
	{
		return MatchingRoomType.COMMAND_CHANNEL;
	}
	
	@Override
	public MatchingMemberType getMemberType(PlayerInstance player)
	{
		if (isLeader(player))
		{
			return MatchingMemberType.COMMAND_CHANNEL_LEADER;
		}
		
		final Party playerParty = player.getParty();
		if (playerParty == null)
		{
			return MatchingMemberType.WAITING_PLAYER_NO_PARTY;
		}
		
		final Party leaderParty = getLeader().getParty();
		if (leaderParty != null)
		{
			final CommandChannel cc = leaderParty.getCommandChannel();
			if ((leaderParty == playerParty) || ((cc != null) && cc.getParties().contains(playerParty)))
			{
				return MatchingMemberType.COMMAND_CHANNEL_PARTY_MEMBER;
			}
		}
		
		return MatchingMemberType.WAITING_PARTY;
	}
}
