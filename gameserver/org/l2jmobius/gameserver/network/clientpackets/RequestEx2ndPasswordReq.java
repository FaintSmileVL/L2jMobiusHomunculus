package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.SecondaryAuthData;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.Ex2ndPasswordAck;
import org.l2jmobius.gameserver.security.SecondaryPasswordAuth;

/**
 * (ch)cS{S} c: change pass? S: current password S: new password
 * @author mrTJO
 */
public class RequestEx2ndPasswordReq implements IClientIncomingPacket
{
	private int _changePass;
	private String _password;
	private String _newPassword;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_changePass = packet.readC();
		_password = packet.readS();
		if (_changePass == 2)
		{
			_newPassword = packet.readS();
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if (!SecondaryAuthData.getInstance().isEnabled())
		{
			return;
		}
		
		final SecondaryPasswordAuth secondAuth = client.getSecondaryAuth();
		boolean success = false;
		if ((_changePass == 0) && !secondAuth.passwordExist())
		{
			success = secondAuth.savePassword(_password);
		}
		else if ((_changePass == 2) && secondAuth.passwordExist())
		{
			success = secondAuth.changePassword(_password, _newPassword);
		}
		
		if (success)
		{
			client.sendPacket(new Ex2ndPasswordAck(_changePass, Ex2ndPasswordAck.SUCCESS));
		}
	}
}
