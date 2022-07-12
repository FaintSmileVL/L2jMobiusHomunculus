package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExHomunculusPointInfo implements IClientOutgoingPacket
{
	@SuppressWarnings("unused")
	private final PlayerInstance _player;
	
	public ExHomunculusPointInfo(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_HOMUNCULUS_POINT_INFO.writeId(packet);
		
		packet.writeD(0); // enchant points
		packet.writeD(0); // hunted monster points
		packet.writeD(0); // points obtained
		packet.writeD(0); // reset time
		packet.writeD(0); // vitality points?
		packet.writeD(0); // vitality points obtained?
		packet.writeD(0); // vitality points reset time?
		
		return true;
	}
}
