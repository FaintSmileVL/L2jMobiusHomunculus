package l2j.network.serverpackets;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.GameServerTable;
import l2j.GameServerInfo;
import l2j.network.LoginClient;
import l2j.network.OutgoingPackets;
import l2j.network.gameserverpackets.ServerStatus;

/**
 * ServerList
 * 
 * <pre>
 * Format: cc [cddcchhcdc]
 * 
 * c: server list size (number of servers)
 * c: ?
 * [ (repeat for each servers)
 * c: server id (ignored by client?)
 * d: server ip
 * d: server port
 * c: age limit (used by client?)
 * c: pvp or not (used by client?)
 * h: current number of players
 * h: max number of players
 * c: 0 if server is down
 * d: 2nd bit: clock
 *    3rd bit: won't display server name
 *    4th bit: test server (used by client?)
 * c: 0 if you don't want to display brackets in front of sever name
 * ]
 * </pre>
 * 
 * Server will be considered as Good when the number of online players<br>
 * is less than half the maximum. as Normal between half and 4/5<br>
 * and Full when there's more than 4/5 of the maximum number of players.
 */
public class ServerList implements IOutgoingPacket
{
	protected static final Logger LOGGER = Logger.getLogger(ServerList.class.getName());
	
	private final List<ServerData> _servers;
	private final int _lastServer;
	private final Map<Integer, Integer> _charsOnServers;
	
	class ServerData
	{
		protected byte[] _ip;
		protected int _port;
		protected int _ageLimit;
		protected boolean _pvp;
		protected int _currentPlayers;
		protected int _maxPlayers;
		protected boolean _brackets;
		protected boolean _clock;
		protected int _status;
		protected int _serverId;
		protected int _serverType;
		
		ServerData(LoginClient client, GameServerInfo gsi)
		{
			try
			{
				_ip = InetAddress.getByName(gsi.getServerAddress(client.getConnectionAddress())).getAddress();
			}
			catch (UnknownHostException e)
			{
				LOGGER.warning(getClass().getSimpleName() + ": " + e.getMessage());
				_ip = new byte[4];
				_ip[0] = 127;
				_ip[1] = 0;
				_ip[2] = 0;
				_ip[3] = 1;
			}
			
			_port = gsi.getPort();
			_pvp = gsi.isPvp();
			_serverType = gsi.getServerType();
			_currentPlayers = gsi.getCurrentPlayerCount();
			_maxPlayers = gsi.getMaxPlayers();
			_ageLimit = 0;
			_brackets = gsi.isShowingBrackets();
			// If server GM-only - show status only to GMs
			_status = (client.getAccessLevel() < 0) || ((gsi.getStatus() == ServerStatus.STATUS_GM_ONLY) && (client.getAccessLevel() <= 0)) ? ServerStatus.STATUS_DOWN : gsi.getStatus();
			_serverId = gsi.getId();
		}
	}
	
	public ServerList(LoginClient client)
	{
		_servers = new ArrayList<>(GameServerTable.getInstance().getRegisteredGameServers().size());
		_lastServer = client.getLastServer();
		for (GameServerInfo gsi : GameServerTable.getInstance().getRegisteredGameServers().values())
		{
			_servers.add(new ServerData(client, gsi));
		}
		_charsOnServers = client.getCharsOnServ();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SERVER_LIST.writeId(packet);
		packet.writeC(_servers.size());
		packet.writeC(_lastServer);
		for (ServerData server : _servers)
		{
			packet.writeC(server._serverId); // server id
			
			packet.writeC(server._ip[0] & 0xff);
			packet.writeC(server._ip[1] & 0xff);
			packet.writeC(server._ip[2] & 0xff);
			packet.writeC(server._ip[3] & 0xff);
			
			packet.writeD(server._port);
			packet.writeC(server._ageLimit); // Age Limit 0, 15, 18
			packet.writeC(server._pvp ? 0x01 : 0x00);
			packet.writeH(server._currentPlayers);
			packet.writeH(server._maxPlayers);
			packet.writeC(server._status == ServerStatus.STATUS_DOWN ? 0x00 : 0x01);
			packet.writeD(server._serverType); // 1: Normal, 2: Relax, 4: Public Test, 8: No Label, 16: Character Creation Restricted, 32: Event, 64: Free
			packet.writeC(server._brackets ? 0x01 : 0x00);
		}
		packet.writeH(0xA4); // unknown
		if (_charsOnServers != null)
		{
			for (ServerData server : _servers)
			{
				packet.writeC(server._serverId);
				packet.writeC(_charsOnServers.getOrDefault(server._serverId, 0));
			}
		}
		return true;
	}
}
