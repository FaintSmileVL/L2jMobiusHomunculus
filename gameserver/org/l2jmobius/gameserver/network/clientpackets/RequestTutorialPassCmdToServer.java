package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.handler.AdminCommandHandler;
import org.l2jmobius.gameserver.handler.BypassHandler;
import org.l2jmobius.gameserver.handler.IBypassHandler;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerBypass;
import org.l2jmobius.gameserver.network.GameClient;

public class RequestTutorialPassCmdToServer implements IClientIncomingPacket
{
	private String _bypass = null;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_bypass = packet.readS();
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
		
		if (_bypass.startsWith("admin_"))
		{
			AdminCommandHandler.getInstance().useAdminCommand(player, _bypass, true);
		}
		else
		{
			final IBypassHandler handler = BypassHandler.getInstance().getHandler(_bypass);
			if (handler != null)
			{
				handler.useBypass(_bypass, player, null);
			}
		}
		EventDispatcher.getInstance().notifyEventAsync(new OnPlayerBypass(player, _bypass), player);
	}
}
