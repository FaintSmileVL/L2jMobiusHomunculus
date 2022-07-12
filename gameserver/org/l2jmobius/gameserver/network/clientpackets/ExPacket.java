package org.l2jmobius.gameserver.network.clientpackets;

import network.IIncomingPacket;
import network.PacketReader;
import org.l2jmobius.gameserver.network.ExIncomingPackets;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Nos
 */
public class ExPacket implements IClientIncomingPacket
{
	// private static final Logger LOGGER = Logger.getLogger(ExPacket.class.getName());
	
	private ExIncomingPackets _exIncomingPacket;
	private IIncomingPacket<GameClient> _exPacket;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int exPacketId = packet.readH() & 0xFFFF;
		if ((exPacketId < 0) || (exPacketId >= ExIncomingPackets.PACKET_ARRAY.length))
		{
			return false;
		}
		
		_exIncomingPacket = ExIncomingPackets.PACKET_ARRAY[exPacketId];
		if (_exIncomingPacket == null)
		{
			// LOGGER.finer(getClass().getSimpleName() + ": Unknown packet: " + Integer.toHexString(exPacketId));
			return false;
		}
		
		_exPacket = _exIncomingPacket.newIncomingPacket();
		return (_exPacket != null) && _exPacket.read(client, packet);
	}
	
	@Override
	public void run(GameClient client) throws Exception
	{
		if (!_exIncomingPacket.getConnectionStates().contains(client.getConnectionState()))
		{
			// LOGGER.finer(_exIncomingPacket + ": Connection at invalid state: " + client.getConnectionState() + " Required State: " + _exIncomingPacket.getConnectionStates());
			return;
		}
		_exPacket.run(client);
	}
}
