package org.l2jmobius.gameserver.network;

import network.IConnectionState;

/**
 * @author KenM
 */
public enum ConnectionState implements IConnectionState
{
	CONNECTED,
	DISCONNECTED,
	CLOSING,
	AUTHENTICATED,
	ENTERING,
	IN_GAME;
}
