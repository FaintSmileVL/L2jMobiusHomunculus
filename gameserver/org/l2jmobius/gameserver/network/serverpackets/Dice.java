package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class Dice implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _itemId;
	private final int _number;
	private final int _x;
	private final int _y;
	private final int _z;
	
	/**
	 * @param charObjId
	 * @param itemId
	 * @param number
	 * @param x
	 * @param y
	 * @param z
	 */
	public Dice(int charObjId, int itemId, int number, int x, int y, int z)
	{
		_objectId = charObjId;
		_itemId = itemId;
		_number = number;
		_x = x;
		_y = y;
		_z = z;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.DICE.writeId(packet);
		
		packet.writeD(_objectId); // object id of player
		packet.writeD(_itemId); // item id of dice (spade) 4625,4626,4627,4628
		packet.writeD(_number); // number rolled
		packet.writeD(_x); // x
		packet.writeD(_y); // y
		packet.writeD(_z); // z
		return true;
	}
}
