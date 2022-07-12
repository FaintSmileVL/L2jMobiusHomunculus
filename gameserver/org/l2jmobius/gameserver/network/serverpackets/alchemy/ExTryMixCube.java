package org.l2jmobius.gameserver.network.serverpackets.alchemy;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.TryMixCubeType;
import org.l2jmobius.gameserver.model.holders.AlchemyResult;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExTryMixCube implements IClientOutgoingPacket
{
	private final TryMixCubeType _type;
	private final List<AlchemyResult> _items = new ArrayList<>();
	
	public ExTryMixCube(TryMixCubeType type)
	{
		_type = type;
	}
	
	public void addItem(AlchemyResult item)
	{
		_items.add(item);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TRY_MIX_CUBE.writeId(packet);
		
		packet.writeC(_type.ordinal());
		packet.writeD(_items.size());
		for (AlchemyResult holder : _items)
		{
			packet.writeC(holder.getType().ordinal());
			packet.writeD(holder.getId());
			packet.writeQ(holder.getCount());
		}
		return true;
	}
}