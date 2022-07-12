package org.l2jmobius.gameserver.network.serverpackets.fishing;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.interfaces.ILocational;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExUserInfoFishing implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final boolean _isFishing;
	private final ILocational _baitLocation;
	
	public ExUserInfoFishing(PlayerInstance player, boolean isFishing, ILocational baitLocation)
	{
		_player = player;
		_isFishing = isFishing;
		_baitLocation = baitLocation;
	}
	
	public ExUserInfoFishing(PlayerInstance player, boolean isFishing)
	{
		_player = player;
		_isFishing = isFishing;
		_baitLocation = null;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_USER_INFO_FISHING.writeId(packet);
		
		packet.writeD(_player.getObjectId());
		packet.writeC(_isFishing ? 1 : 0);
		if (_baitLocation == null)
		{
			packet.writeD(0);
			packet.writeD(0);
			packet.writeD(0);
		}
		else
		{
			packet.writeD(_baitLocation.getX());
			packet.writeD(_baitLocation.getY());
			packet.writeD(_baitLocation.getZ());
		}
		return true;
	}
}
