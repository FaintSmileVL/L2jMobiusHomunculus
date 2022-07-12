package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

public class ExHomonculusSummonResult implements IClientOutgoingPacket
{
	@SuppressWarnings("unused")
	private final PlayerInstance _player;
	
	public ExHomonculusSummonResult(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_HOMUNCULUS_LIST.writeId(packet);
		
		packet.writeD(1);
		packet.writeD(2);
		packet.writeD(1); // Homunculus client id?
		
		return true;
	}
}
