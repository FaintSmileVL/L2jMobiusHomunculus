package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.Shortcut;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.taskmanager.AutoUseTaskManager;

/**
 * @version $Revision: 1.3.4.2 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestShortCutDel implements IClientIncomingPacket
{
	private int _id;
	private int _slot;
	private int _page;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_id = packet.readD();
		_slot = _id % 12;
		_page = _id / 12;
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
		
		if ((_page > 23) || (_page < 0))
		{
			return;
		}
		
		// Store shortcut reference id.
		final Shortcut shortcut = player.getShortCut(_slot, _page);
		final int id = shortcut == null ? -1 : shortcut.getId();
		
		// Delete the shortcut.
		player.deleteShortCut(_slot, _page);
		
		// Remove auto used ids.
		if (_slot > 263)
		{
			AutoUseTaskManager.getInstance().removeAutoSupplyItem(player, id);
		}
		else
		{
			AutoUseTaskManager.getInstance().removeAutoSkill(player, id);
		}
	}
}
