package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExHomunculusInsertHpSpVp implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExHomunculusInsertHpSpVp(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_HOMUNCULUS_HPSPVP.writeId(packet);
		
		packet.writeD((int) _player.getCurrentHp());
		packet.writeQ(_player.getSp());
		packet.writeD(_player.getVitalityPoints());
		
		return true;
	}
}
