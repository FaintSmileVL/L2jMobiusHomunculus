package network.codecs;

import java.util.List;
import java.util.logging.Logger;

import network.IConnectionState;
import network.IIncomingPacket;
import network.IIncomingPackets;
import network.PacketReader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author Nos
 * @param <T>
 */
public class PacketDecoder<T>extends ByteToMessageDecoder
{
	private static final Logger LOGGER = Logger.getLogger(PacketDecoder.class.getName());
	
	private final IIncomingPackets<T>[] _incomingPackets;
	private final T _client;
	
	public PacketDecoder(IIncomingPackets<T>[] incomingPackets, T client)
	{
		_incomingPackets = incomingPackets;
		_client = client;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
	{
		if ((in == null) || !in.isReadable())
		{
			return;
		}
		
		try
		{
			final short packetId = in.readUnsignedByte();
			if (packetId >= _incomingPackets.length)
			{
				LOGGER.finer("Unknown packet: " + Integer.toHexString(packetId));
				return;
			}
			
			final IIncomingPackets<T> incomingPacket = _incomingPackets[packetId];
			if (incomingPacket == null)
			{
				LOGGER.finer("Unknown packet: " + Integer.toHexString(packetId));
				return;
			}
			
			final IConnectionState connectionState = ctx.channel().attr(IConnectionState.ATTRIBUTE_KEY).get();
			if ((connectionState == null) || !incomingPacket.getConnectionStates().contains(connectionState))
			{
				// LOGGER.warning(incomingPacket + ": Connection at invalid state: " + connectionState + " Required States: " + incomingPacket.getConnectionStates());
				return;
			}
			
			final IIncomingPacket<T> packet = incomingPacket.newIncomingPacket();
			if ((packet != null) && packet.read(_client, new PacketReader(in)))
			{
				out.add(packet);
			}
		}
		finally
		{
			// We always consider that we read whole packet.
			in.readerIndex(in.writerIndex());
		}
	}
}
