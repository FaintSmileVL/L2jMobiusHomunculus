package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Dialog with input field<br>
 * type 0 = char name (Selection screen)<br>
 * type 1 = clan name
 * @author JIV
 */
public class ExNeedToChangeName implements IClientOutgoingPacket
{
	private final int _type;
	private final int _subType;
	private final String _name;
	
	public ExNeedToChangeName(int type, int subType, String name)
	{
		super();
		_type = type;
		_subType = subType;
		_name = name;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_NEED_TO_CHANGE_NAME.writeId(packet);
		
		packet.writeD(_type);
		packet.writeD(_subType);
		packet.writeS(_name);
		return true;
	}
}
