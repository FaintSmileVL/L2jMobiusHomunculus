package l2j.network;

import network.IConnectionState;

/**
 * @author Mobius
 */
public enum ConnectionState implements IConnectionState
{
	CONNECTED,
	AUTHED_GG,
	AUTHED_LOGIN;
}
