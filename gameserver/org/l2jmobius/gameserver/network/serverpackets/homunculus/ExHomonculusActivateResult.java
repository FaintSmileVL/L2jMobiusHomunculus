package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExHomonculusActivateResult implements IClientOutgoingPacket
{
	@SuppressWarnings("unused")
	private final PlayerInstance _player;
	
	public ExHomonculusActivateResult(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ACTIVATE_HOMUNCULUS_RESULT.writeId(packet);
		
		// struct _C_EX_REQUEST_ACTIVATE_HOMUNCULUS
		// {
		// var int nIdx;
		// var byte bActivate;
		// };
		
		packet.writeD(0x01);
		packet.writeC(0x01);
		
		return true;
	}
}
