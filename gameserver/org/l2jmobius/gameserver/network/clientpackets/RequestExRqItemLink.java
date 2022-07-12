package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExRpItemLink;

/**
 * @author KenM
 */
public class RequestExRqItemLink implements IClientIncomingPacket
{
	private int _objectId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final WorldObject object = World.getInstance().findObject(_objectId);
		if ((object != null) && object.isItem())
		{
			final ItemInstance item = (ItemInstance) object;
			if (item.isPublished())
			{
				client.sendPacket(new ExRpItemLink(item));
			}
		}
	}
}
