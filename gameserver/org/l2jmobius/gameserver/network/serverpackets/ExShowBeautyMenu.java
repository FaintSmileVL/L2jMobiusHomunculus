package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExShowBeautyMenu implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _type;
	
	// TODO: Enum
	public static final int MODIFY_APPEARANCE = 0;
	public static final int RESTORE_APPEARANCE = 1;
	
	public ExShowBeautyMenu(PlayerInstance player, int type)
	{
		_player = player;
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_BEAUTY_MENU.writeId(packet);
		
		packet.writeD(_type);
		packet.writeD(_player.getVisualHair());
		packet.writeD(_player.getVisualHairColor());
		packet.writeD(_player.getVisualFace());
		return true;
	}
}