package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * ExVoteSystemInfo packet implementation.
 * @author Gnacik
 */
public class ExVoteSystemInfo implements IClientOutgoingPacket
{
	private final int _recomLeft;
	private final int _recomHave;
	private final int _bonusTime;
	private final int _bonusVal;
	private final int _bonusType;
	
	public ExVoteSystemInfo(PlayerInstance player)
	{
		_recomLeft = player.getRecomLeft();
		_recomHave = player.getRecomHave();
		_bonusTime = 0;
		_bonusVal = 0;
		_bonusType = 0;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_VOTE_SYSTEM_INFO.writeId(packet);
		
		packet.writeD(_recomLeft);
		packet.writeD(_recomHave);
		packet.writeD(_bonusTime);
		packet.writeD(_bonusVal);
		packet.writeD(_bonusType);
		return true;
	}
}
