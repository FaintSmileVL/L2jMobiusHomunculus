package l2j.network;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import network.ChannelInboundHandler;
import network.IIncomingPacket;
import network.IOutgoingPacket;
import util.Chronos;
import util.Rnd;
import util.crypt.ScrambledKeyPair;
import l2j.LoginController;
import l2j.SessionKey;
import l2j.network.serverpackets.Init;
import l2j.network.serverpackets.LoginFail;
import l2j.network.serverpackets.LoginFail.LoginFailReason;
import l2j.network.serverpackets.PlayFail;
import l2j.network.serverpackets.PlayFail.PlayFailReason;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Represents a client connected into the LoginServer
 * @author KenM
 */
public class LoginClient extends ChannelInboundHandler<LoginClient>
{
	private static final Logger LOGGER = Logger.getLogger(LoginClient.class.getName());
	
	// Crypt
	private final ScrambledKeyPair _scrambledPair;
	private final SecretKey _blowfishKey;
	private InetAddress _addr;
	private Channel _channel;
	
	private String _account;
	private int _accessLevel;
	private int _lastServer;
	private SessionKey _sessionKey;
	private int _sessionId;
	private boolean _joinedGS;
	private Map<Integer, Integer> _charsOnServers;
	private Map<Integer, long[]> _charsToDelete;
	
	private long _connectionStartTime;
	
	public LoginClient(SecretKey blowfishKey)
	{
		super();
		_blowfishKey = blowfishKey;
		_scrambledPair = LoginController.getInstance().getScrambledRSAKeyPair();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		super.channelActive(ctx);
		
		setConnectionState(ConnectionState.CONNECTED);
		final InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
		_addr = address.getAddress();
		_channel = ctx.channel();
		_sessionId = Rnd.nextInt();
		_connectionStartTime = Chronos.currentTimeMillis();
		sendPacket(new Init(_scrambledPair.getScrambledModulus(), _blowfishKey.getEncoded(), _sessionId));
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx)
	{
		if (!_joinedGS || ((_connectionStartTime + LoginController.LOGIN_TIMEOUT) < Chronos.currentTimeMillis()))
		{
			LoginController.getInstance().removeAuthedLoginClient(getAccount());
		}
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IIncomingPacket<LoginClient> packet)
	{
		try
		{
			packet.run(this);
		}
		catch (Exception e)
		{
			LOGGER.warning(getClass().getSimpleName() + ": " + e.getMessage());
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
	}
	
	public InetAddress getConnectionAddress()
	{
		return _addr;
	}
	
	public String getAccount()
	{
		return _account;
	}
	
	public void setAccount(String account)
	{
		_account = account;
	}
	
	public void setAccessLevel(int accessLevel)
	{
		_accessLevel = accessLevel;
	}
	
	public int getAccessLevel()
	{
		return _accessLevel;
	}
	
	public void setLastServer(int lastServer)
	{
		_lastServer = lastServer;
	}
	
	public int getLastServer()
	{
		return _lastServer;
	}
	
	public int getSessionId()
	{
		return _sessionId;
	}
	
	public ScrambledKeyPair getScrambledKeyPair()
	{
		return _scrambledPair;
	}
	
	public boolean hasJoinedGS()
	{
		return _joinedGS;
	}
	
	public void setJoinedGS(boolean value)
	{
		_joinedGS = value;
	}
	
	public void setSessionKey(SessionKey sessionKey)
	{
		_sessionKey = sessionKey;
	}
	
	public SessionKey getSessionKey()
	{
		return _sessionKey;
	}
	
	public long getConnectionStartTime()
	{
		return _connectionStartTime;
	}
	
	public void sendPacket(IOutgoingPacket packet)
	{
		if ((packet == null))
		{
			return;
		}
		
		// Write into the channel.
		_channel.writeAndFlush(packet);
	}
	
	public void close(LoginFailReason reason)
	{
		close(new LoginFail(reason));
	}
	
	public void close(PlayFailReason reason)
	{
		close(new PlayFail(reason));
	}
	
	public void close(IOutgoingPacket packet)
	{
		sendPacket(packet);
		closeNow();
	}
	
	public void closeNow()
	{
		if (_channel != null)
		{
			_channel.close();
		}
	}
	
	public void setCharsOnServ(int servId, int chars)
	{
		if (_charsOnServers == null)
		{
			_charsOnServers = new HashMap<>();
		}
		_charsOnServers.put(servId, chars);
	}
	
	public Map<Integer, Integer> getCharsOnServ()
	{
		return _charsOnServers;
	}
	
	public void serCharsWaitingDelOnServ(int servId, long[] charsToDel)
	{
		if (_charsToDelete == null)
		{
			_charsToDelete = new HashMap<>();
		}
		_charsToDelete.put(servId, charsToDel);
	}
	
	public Map<Integer, long[]> getCharsWaitingDelOnServ()
	{
		return _charsToDelete;
	}
}
