package org.l2jmobius.gameserver.network.loginserver;

import java.util.logging.Logger;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.network.EventLoopGroupManager;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author NosBit
 */
public class LoginServerNetworkManager
{
	private static final Logger LOGGER = Logger.getLogger(LoginServerNetworkManager.class.getName());
	
	private final Bootstrap _bootstrap;
	
	private ChannelFuture _channelFuture;
	
	public LoginServerNetworkManager()
	{
		//@formatter:off
		_bootstrap = new Bootstrap()
			.group(EventLoopGroupManager.getInstance().getWorkerGroup())
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new LoginServerInitializer());
		//@formatter:on
	}
	
	public ChannelFuture getChannelFuture()
	{
		return _channelFuture;
	}
	
	public void connect() throws InterruptedException
	{
		if ((_channelFuture != null) && _channelFuture.isSuccess())
		{
			return;
		}
		_channelFuture = _bootstrap.connect(Config.GAME_SERVER_LOGIN_HOST, Config.GAME_SERVER_LOGIN_PORT).sync();
		LOGGER.info("Connected to " + Config.GAME_SERVER_LOGIN_HOST + ":" + Config.GAME_SERVER_LOGIN_PORT);
	}
	
	public void disconnect() throws InterruptedException
	{
		_channelFuture.channel().close().sync();
	}
	
	public static LoginServerNetworkManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final LoginServerNetworkManager INSTANCE = new LoginServerNetworkManager();
	}
}
