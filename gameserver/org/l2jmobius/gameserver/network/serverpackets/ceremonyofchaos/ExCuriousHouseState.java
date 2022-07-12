package org.l2jmobius.gameserver.network.serverpackets.ceremonyofchaos;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExCuriousHouseState implements IClientOutgoingPacket
{
	public static final ExCuriousHouseState IDLE_PACKET = new ExCuriousHouseState(0);
	public static final ExCuriousHouseState REGISTRATION_PACKET = new ExCuriousHouseState(1);
	public static final ExCuriousHouseState PREPARE_PACKET = new ExCuriousHouseState(2);
	public static final ExCuriousHouseState STARTING_PACKET = new ExCuriousHouseState(3);
	
	private final int _state;
	
	public ExCuriousHouseState(int state)
	{
		_state = state;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CURIOUS_HOUSE_STATE.writeId(packet);
		packet.writeD(_state);
		return true;
	}
}