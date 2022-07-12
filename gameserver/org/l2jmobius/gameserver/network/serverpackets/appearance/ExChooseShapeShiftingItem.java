package org.l2jmobius.gameserver.network.serverpackets.appearance;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceStone;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceTargetType;
import org.l2jmobius.gameserver.model.items.appearance.AppearanceType;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author UnAfraid
 */
public class ExChooseShapeShiftingItem implements IClientOutgoingPacket
{
	private final AppearanceType _type;
	private final AppearanceTargetType _targetType;
	private final int _itemId;
	
	public ExChooseShapeShiftingItem(AppearanceStone stone)
	{
		_type = stone.getType();
		_targetType = stone.getTargetTypes().size() > 1 ? AppearanceTargetType.ALL : stone.getTargetTypes().get(0);
		_itemId = stone.getId();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHOOSE_SHAPE_SHIFTING_ITEM.writeId(packet);
		
		packet.writeD(_targetType != null ? _targetType.ordinal() : 0);
		packet.writeD(_type != null ? _type.ordinal() : 0);
		packet.writeD(_itemId);
		return true;
	}
}
