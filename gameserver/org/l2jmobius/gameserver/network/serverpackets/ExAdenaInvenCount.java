package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExAdenaInvenCount implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExAdenaInvenCount(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ADENA_INVEN_COUNT.writeId(packet);
		
		packet.writeQ(_player.getAdena());
		packet.writeH(_player.getInventory().getSize());
		return true;
	}
}