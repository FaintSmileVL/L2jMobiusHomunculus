package l2j;import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Chronos;

/**
 * @author -Wooden-
 */
public abstract class FloodProtectedListener extends Thread
{
	private static final Logger LOGGER = Logger.getLogger(FloodProtectedListener.class.getName());
	private final Map<String, ForeignConnection> _floodProtection = new ConcurrentHashMap<>();
	private ServerSocket _serverSocket;
	
	public FloodProtectedListener(String listenIp, int port) throws IOException
	{
		if (listenIp.equals("*"))
		{
			_serverSocket = new ServerSocket(port);
		}
		else
		{
			_serverSocket = new ServerSocket(port, 50, InetAddress.getByName(listenIp));
		}
	}
	
	@Override
	public void run()
	{
		Socket connection = null;
		while (!isInterrupted())
		{
			try
			{
				connection = _serverSocket.accept();
				if (AuthConfig.FLOOD_PROTECTION)
				{
					ForeignConnection fConnection = _floodProtection.get(connection.getInetAddress().getHostAddress());
					if (fConnection != null)
					{
						fConnection.connectionNumber += 1;
						if (((fConnection.connectionNumber > AuthConfig.FAST_CONNECTION_LIMIT) && ((Chronos.currentTimeMillis() - fConnection.lastConnection) < AuthConfig.NORMAL_CONNECTION_TIME)) || ((Chronos.currentTimeMillis() - fConnection.lastConnection) < AuthConfig.FAST_CONNECTION_TIME) || (fConnection.connectionNumber > AuthConfig.MAX_CONNECTION_PER_IP))
						{
							fConnection.lastConnection = Chronos.currentTimeMillis();
							connection.close();
							fConnection.connectionNumber -= 1;
							if (!fConnection.isFlooding)
							{
								LOGGER.warning("Potential Flood from " + connection.getInetAddress().getHostAddress());
							}
							fConnection.isFlooding = true;
							continue;
						}
						if (fConnection.isFlooding) // if connection was flooding server but now passed the check
						{
							fConnection.isFlooding = false;
							LOGGER.info(connection.getInetAddress().getHostAddress() + " is not considered as flooding anymore.");
						}
						fConnection.lastConnection = Chronos.currentTimeMillis();
					}
					else
					{
						fConnection = new ForeignConnection(Chronos.currentTimeMillis());
						_floodProtection.put(connection.getInetAddress().getHostAddress(), fConnection);
					}
				}
				
				addClient(connection);
			}
			catch (Exception e)
			{
				if (isInterrupted())
				{
					// shutdown?
					try
					{
						_serverSocket.close();
					}
					catch (IOException io)
					{
						LOGGER.log(Level.INFO, "", io);
					}
					break;
				}
			}
		}
	}
	
	protected static class ForeignConnection
	{
		public int connectionNumber;
		public long lastConnection;
		public boolean isFlooding = false;
		
		/**
		 * @param time
		 */
		public ForeignConnection(long time)
		{
			lastConnection = time;
			connectionNumber = 1;
		}
	}
	
	public abstract void addClient(Socket s);
	
	public void removeFloodProtection(String ip)
	{
		if (!AuthConfig.FLOOD_PROTECTION)
		{
			return;
		}
		final ForeignConnection fConnection = _floodProtection.get(ip);
		if (fConnection != null)
		{
			fConnection.connectionNumber -= 1;
			if (fConnection.connectionNumber == 0)
			{
				_floodProtection.remove(ip);
			}
		}
		else
		{
			LOGGER.warning("Removing a flood protection for a GameServer that was not in the connection map??? :" + ip);
		}
	}
	
	public void close()
	{
		try
		{
			_serverSocket.close();
		}
		catch (IOException e)
		{
			LOGGER.warning(getClass().getSimpleName() + ": " + e.getMessage());
		}
	}
}