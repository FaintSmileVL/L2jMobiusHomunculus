package network;

import java.util.logging.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Nos
 */
public class NetworkManager
{
	private static final Logger LOGGER = Logger.getLogger(NetworkManager.class.getName());
	
	private final ServerBootstrap _serverBootstrap;
	private final String _host;
	private final int _port;
	
	private ChannelFuture _channelFuture;
	
	public NetworkManager(EventLoopGroup bossGroup, EventLoopGroup workerGroup, ChannelInitializer<SocketChannel> clientInitializer, String host, int port)
	{
		// @formatter:off
		_serverBootstrap = new ServerBootstrap()
			.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(clientInitializer)
			.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		// @formatter:on
		_host = host;
		_port = port;
	}
	
	public ChannelFuture getChannelFuture()
	{
		return _channelFuture;
	}
	
	public void start() throws InterruptedException
	{
		if ((_channelFuture != null) && !_channelFuture.isDone())
		{
			return;
		}
		
		_channelFuture = _serverBootstrap.bind(_host, _port).sync();
		LOGGER.info(getClass().getSimpleName() + ": Listening on " + _host + ":" + _port);
	}
	
	public void stop() throws InterruptedException
	{
		_channelFuture.channel().close().sync();
	}
}
