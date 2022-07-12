package l2j.network;

import l2j.AuthConfig;
import network.NetworkManager;

/**
 * @author Nos
 */
public class ClientNetworkManager extends NetworkManager
{
	protected ClientNetworkManager()
	{
		super(EventLoopGroupManager.getInstance().getBossGroup(), EventLoopGroupManager.getInstance().getWorkerGroup(), new ClientInitializer(), AuthConfig.LOGIN_BIND_ADDRESS, AuthConfig.PORT_LOGIN);
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
