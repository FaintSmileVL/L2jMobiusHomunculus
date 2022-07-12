package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Mobius
 */
public class RequestTargetActionMenu implements IClientIncomingPacket
{
	private int _objectId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		packet.readH(); // action?
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || player.isTargetingDisabled())
		{
			return;
		}
		
		for (WorldObject object : World.getInstance().getVisibleObjects(player, WorldObject.class))
		{
			if (_objectId == object.getObjectId())
			{
				if (object.isTargetable() && object.isAutoAttackable(player))
				{
					player.setTarget(object);
				}
				break;
			}
		}
	}
}
