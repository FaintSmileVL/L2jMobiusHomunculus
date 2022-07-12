package network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Nos
 * @param <T>
 */
public abstract class ChannelInboundHandler<T extends ChannelInboundHandler<?>>extends SimpleChannelInboundHandler<IIncomingPacket<T>>
{
	private Channel _channel;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		_channel = ctx.channel();
	}
	
	public void setConnectionState(IConnectionState connectionState)
	{
		_channel.attr(IConnectionState.ATTRIBUTE_KEY).set(connectionState);
	}
	
	public IConnectionState getConnectionState()
	{
		return _channel != null ? _channel.attr(IConnectionState.ATTRIBUTE_KEY).get() : null;
	}
}
