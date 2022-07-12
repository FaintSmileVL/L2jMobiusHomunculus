package l2j.network.loginserverpackets;

import network.BaseSendablePacket;
import l2j.GameServerTable;

/**
 * @author -Wooden-
 */
public class AuthResponse extends BaseSendablePacket
{
	/**
	 * @param serverId
	 */
	public AuthResponse(int serverId)
	{
		writeC(0x02);
		writeC(serverId);
		writeS(GameServerTable.getInstance().getServerNameById(serverId));
	}
	
	@Override
	public byte[] getContent()
	{
		return getBytes();
	}
}
