package org.l2jmobius.gameserver.network.loginserver;

import network.ChannelInboundHandler;
import network.IIncomingPacket;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author NosBit
 */
public class LoginServerHandler extends ChannelInboundHandler<LoginServerHandler>
{
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IIncomingPacket<LoginServerHandler> msg) throws Exception
	{
		msg.run(this);
	}
}
