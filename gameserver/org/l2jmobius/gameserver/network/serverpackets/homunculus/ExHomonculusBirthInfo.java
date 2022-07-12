package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExHomonculusBirthInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExHomonculusBirthInfo(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_BIRTH_INFO.writeId(packet);
		
		final int status = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_STATUS, 0);
		final int hp = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_HP, 0);
		final int sp = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_SP, 0);
		final int vp = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_VP, 0);
		final int time = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_TIME, 0);
		final long currentTime = Chronos.currentTimeMillis();
		
		packet.writeD(status); // 0 = time idle, 1 = time updating, 2 = summon enabled
		packet.writeD(hp); // hp 100
		packet.writeD(sp); // sp 10
		packet.writeD(vp); // vitality 5
		packet.writeQ((currentTime / 1000) + (86400 - Math.min(86400, (currentTime / 1000) - time)));
		
		return true;
	}
}
