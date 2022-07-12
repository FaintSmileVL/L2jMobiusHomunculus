package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.PremiumItem;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Gnacik
 */
public class ExGetPremiumItemList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	private final Map<Integer, PremiumItem> _map;
	
	public ExGetPremiumItemList(PlayerInstance player)
	{
		_player = player;
		_map = _player.getPremiumItemList();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_GET_PREMIUM_ITEM_LIST.writeId(packet);
		
		packet.writeD(_map.size());
		for (Entry<Integer, PremiumItem> entry : _map.entrySet())
		{
			final PremiumItem item = entry.getValue();
			packet.writeQ(entry.getKey());
			packet.writeD(item.getItemId());
			packet.writeQ(item.getCount());
			packet.writeD(0x00); // ?
			packet.writeS(item.getSender());
		}
		return true;
	}
}
