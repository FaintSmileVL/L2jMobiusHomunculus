package network.codecs;

import java.util.logging.Level;
import java.util.logging.Logger;

import network.IOutgoingPacket;
import network.PacketWriter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Nos
 */
@Sharable
public class PacketEncoder extends MessageToByteEncoder<IOutgoingPacket>
{
	private static final Logger LOGGER = Logger.getLogger(PacketEncoder.class.getName());
	
	private final int _maxPacketSize;
	
	public PacketEncoder(int maxPacketSize)
	{
		super();
		_maxPacketSize = maxPacketSize;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, IOutgoingPacket packet, ByteBuf out)
	{
		try
		{
			if (packet.write(new PacketWriter(out)))
			{
				if (out.writerIndex() > _maxPacketSize)
				{
					throw new IllegalStateException("Packet (" + packet + ") size (" + out.writerIndex() + ") is bigger than the expected client limit (" + _maxPacketSize + ")");
				}
			}
			else
			{
				// Avoid sending the packet
				out.clear();
			}
		}
		catch (Throwable e)
		{
			LOGGER.log(Level.WARNING, "Failed sending Packet(" + packet + ")", e);
			// Avoid sending the packet if some exception happened
			out.clear();
		}
	}
}