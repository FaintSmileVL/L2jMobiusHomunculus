package org.l2jmobius.gameserver.network.telnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author UnAfraid
 */
public class TelnetServerInitializer extends ChannelInitializer<SocketChannel>
{
	private static final StringEncoder ENCODER = new StringEncoder();
	private static final StringDecoder DECODER = new StringDecoder();
	private static final TelnetServerHandler HANDLER = new TelnetServerHandler();
	
	@Override
	public void initChannel(SocketChannel ch)
	{
		final ChannelPipeline pipeline = ch.pipeline();
		
		// Add the text line codec combination first,
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);
		pipeline.addLast(HANDLER);
	}
}