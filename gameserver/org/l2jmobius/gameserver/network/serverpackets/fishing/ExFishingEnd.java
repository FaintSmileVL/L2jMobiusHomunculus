package org.l2jmobius.gameserver.network.serverpackets.fishing;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author -Wooden-
 */
public class ExFishingEnd implements IClientOutgoingPacket
{
	public enum FishingEndReason
	{
		LOSE(0),
		WIN(1),
		STOP(2);
		
		private final int _reason;
		
		FishingEndReason(int reason)
		{
			_reason = reason;
		}
		
		public int getReason()
		{
			return _reason;
		}
	}
	
	public enum FishingEndType
	{
		PLAYER_STOP,
		PLAYER_CANCEL,
		ERROR;
	}
	
	private final PlayerInstance _player;
	private final FishingEndReason _reason;
	
	public ExFishingEnd(PlayerInstance player, FishingEndReason reason)
	{
		_player = player;
		_reason = reason;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_FISHING_END.writeId(packet);
		packet.writeD(_player.getObjectId());
		packet.writeC(_reason.getReason());
		return true;
	}
}
