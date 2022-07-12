package org.l2jmobius.gameserver.network.loginserver;

import java.nio.ByteOrder;

import network.codecs.LengthFieldBasedFrameEncoder;
import network.codecs.PacketDecoder;
import network.codecs.PacketEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author NosBit
 */
public class LoginServerInitializer extends ChannelInitializer<SocketChannel>
{
	private static final LengthFieldBasedFrameEncoder LENGTH_ENCODER = new LengthFieldBasedFrameEncoder();
	private static final PacketEncoder PACKET_ENCODER = new PacketEncoder(0x8000 - 2);
	
	@Override
	protected void initChannel(SocketChannel ch)
	{
		final LoginServerHandler loginServerHandler = new LoginServerHandler();
		ch.pipeline().addLast("length-decoder", new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 0x8000 - 2, 0, 2, -2, 2, false));
		ch.pipeline().addLast("length-encoder", LENGTH_ENCODER);
		// ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
		ch.pipeline().addLast("packet-decoder", new PacketDecoder<>(IncomingPackets.PACKET_ARRAY, loginServerHandler));
		ch.pipeline().addLast("packet-encoder", PACKET_ENCODER);
		ch.pipeline().addLast(loginServerHandler);
	}
}
