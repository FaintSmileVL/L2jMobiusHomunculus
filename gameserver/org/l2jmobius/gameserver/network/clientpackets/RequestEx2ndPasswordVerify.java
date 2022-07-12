package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SecondaryAuthData;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: (ch)S S: numerical password
 * @author mrTJO
 */
public class RequestEx2ndPasswordVerify implements IClientIncomingPacket
{
	private String _password;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_password = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (!SecondaryAuthData.getInstance().isEnabled())
		{
			return;
		}
		
		client.getSecondaryAuth().checkPassword(_password, false);
	}
}
