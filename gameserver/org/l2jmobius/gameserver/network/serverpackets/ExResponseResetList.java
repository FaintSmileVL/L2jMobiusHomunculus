package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExResponseResetList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExResponseResetList(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_RESET_LIST.writeId(packet);
		
		packet.writeQ(_player.getAdena());
		packet.writeQ(_player.getBeautyTickets());
		
		packet.writeD(_player.getAppearance().getHairStyle());
		packet.writeD(_player.getAppearance().getHairColor());
		packet.writeD(_player.getAppearance().getFace());
		return true;
	}
}
