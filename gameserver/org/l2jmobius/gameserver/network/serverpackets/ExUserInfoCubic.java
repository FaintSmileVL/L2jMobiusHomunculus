package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExUserInfoCubic implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExUserInfoCubic(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_USER_INFO_CUBIC.writeId(packet);
		
		packet.writeD(_player.getObjectId());
		packet.writeH(_player.getCubics().size());
		
		_player.getCubics().keySet().forEach(packet::writeH);
		
		packet.writeD(_player.getAgathionId());
		return true;
	}
}
