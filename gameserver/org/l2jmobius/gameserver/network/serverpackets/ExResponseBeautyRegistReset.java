package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExResponseBeautyRegistReset implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _type;
	private final int _result;
	
	public static final int FAILURE = 0;
	public static final int SUCCESS = 1;
	
	public static final int CHANGE = 0;
	public static final int RESTORE = 1;
	
	public ExResponseBeautyRegistReset(PlayerInstance player, int type, int result)
	{
		_player = player;
		_type = type;
		_result = result;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_BEAUTY_REGIST_RESET.writeId(packet);
		
		packet.writeQ(_player.getAdena());
		packet.writeQ(_player.getBeautyTickets());
		packet.writeD(_type);
		packet.writeD(_result);
		packet.writeD(_player.getVisualHair());
		packet.writeD(_player.getVisualFace());
		packet.writeD(_player.getVisualHairColor());
		return true;
	}
}
