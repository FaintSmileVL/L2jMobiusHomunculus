package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExHomonculusInsertResult implements IClientOutgoingPacket
{
	@SuppressWarnings("unused")
	private final PlayerInstance _player;
	private final int _type;
	
	public ExHomonculusInsertResult(PlayerInstance player, int type)
	{
		_player = player;
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_HOMUNCULUS_INSERT_RESULT.writeId(packet);
		
		packet.writeD(0x01);
		packet.writeD(_type);
		
		return true;
	}
}
