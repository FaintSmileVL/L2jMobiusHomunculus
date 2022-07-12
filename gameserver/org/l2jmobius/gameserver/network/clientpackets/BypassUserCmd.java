package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.handler.IUserCommandHandler;
import org.l2jmobius.gameserver.handler.UserCommandHandler;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @version $Revision: 1.1.2.1.2.2 $ $Date: 2005/03/27 15:29:30 $
 */
public class BypassUserCmd implements IClientIncomingPacket
{
	private int _command;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_command = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		final IUserCommandHandler handler = UserCommandHandler.getInstance().getHandler(_command);
		if (handler == null)
		{
			if (player.isGM())
			{
				player.sendMessage("User commandID " + _command + " not implemented yet.");
			}
		}
		else
		{
			handler.useUserCommand(_command, client.getPlayer());
		}
	}
}
