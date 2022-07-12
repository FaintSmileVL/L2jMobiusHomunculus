package org.l2jmobius.gameserver.network.loginserver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import network.IConnectionState;
import network.IIncomingPacket;
import network.IIncomingPackets;

/**
 * @author NosBit
 */
enum IncomingPackets implements IIncomingPackets<LoginServerHandler>
{
	NONE(0, null);
	
	public static final IncomingPackets[] PACKET_ARRAY;
	
	static
	{
		final short maxPacketId = (short) Arrays.stream(values()).mapToInt(IIncomingPackets::getPacketId).max().orElse(0);
		PACKET_ARRAY = new IncomingPackets[maxPacketId + 1];
		for (IncomingPackets incomingPacket : values())
		{
			PACKET_ARRAY[incomingPacket.getPacketId()] = incomingPacket;
		}
	}
	
	private short _packetId;
	private Supplier<IIncomingPacket<LoginServerHandler>> _incomingPacketFactory;
	private Set<IConnectionState> _connectionStates;
	
	IncomingPackets(int packetId, Supplier<IIncomingPacket<LoginServerHandler>> incomingPacketFactory, IConnectionState... connectionStates)
	{
		// packetId is an unsigned byte
		if (packetId > 0xFF)
		{
			throw new IllegalArgumentException("packetId must not be bigger than 0xFF");
		}
		
		_packetId = (short) packetId;
		_incomingPacketFactory = incomingPacketFactory != null ? incomingPacketFactory : () -> null;
		_connectionStates = new HashSet<>(Arrays.asList(connectionStates));
	}
	
	@Override
	public int getPacketId()
	{
		return _packetId;
	}
	
	@Override
	public IIncomingPacket<LoginServerHandler> newIncomingPacket()
	{
		return _incomingPacketFactory.get();
	}
	
	@Override
	public Set<IConnectionState> getConnectionStates()
	{
		return _connectionStates;
	}
}
