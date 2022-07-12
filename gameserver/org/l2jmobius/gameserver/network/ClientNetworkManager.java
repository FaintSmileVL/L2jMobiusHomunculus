package org.l2jmobius.gameserver.network;

import org.l2jmobius.Config;
import network.NetworkManager;

/**
 * @author Nos
 */
public class ClientNetworkManager extends NetworkManager
{
	protected ClientNetworkManager()
	{
		super(EventLoopGroupManager.getInstance().getBossGroup(), EventLoopGroupManager.getInstance().getWorkerGroup(), new ClientInitializer(), Config.GAMESERVER_HOSTNAME, Config.PORT_GAME);
	}
	
	public static ClientNetworkManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final ClientNetworkManager INSTANCE = new ClientNetworkManager();
	}
}
