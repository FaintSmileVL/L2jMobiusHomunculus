package org.l2jmobius.gameserver.network.serverpackets.attributechange;

import java.util.EnumMap;
import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.AttributeType;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExChangeAttributeInfo implements IClientOutgoingPacket
{
	private static final Map<AttributeType, Byte> ATTRIBUTE_MASKS = new EnumMap<>(AttributeType.class);
	static
	{
		ATTRIBUTE_MASKS.put(AttributeType.FIRE, (byte) 1);
		ATTRIBUTE_MASKS.put(AttributeType.WATER, (byte) 2);
		ATTRIBUTE_MASKS.put(AttributeType.WIND, (byte) 4);
		ATTRIBUTE_MASKS.put(AttributeType.EARTH, (byte) 8);
		ATTRIBUTE_MASKS.put(AttributeType.HOLY, (byte) 16);
		ATTRIBUTE_MASKS.put(AttributeType.DARK, (byte) 32);
	}
	
	private final int _crystalItemId;
	private int _attributes;
	private int _itemObjId;
	
	public ExChangeAttributeInfo(int crystalItemId, ItemInstance item)
	{
		_crystalItemId = crystalItemId;
		_attributes = 0;
		for (AttributeType e : AttributeType.ATTRIBUTE_TYPES)
		{
			if (e != item.getAttackAttributeType())
			{
				_attributes |= ATTRIBUTE_MASKS.get(e);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_ATTRIBUTE_INFO.writeId(packet);
		packet.writeD(_crystalItemId);
		packet.writeD(_attributes);
		packet.writeD(_itemObjId);
		return true;
	}
}