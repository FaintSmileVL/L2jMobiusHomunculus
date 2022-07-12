package org.l2jmobius.gameserver.network.serverpackets.friend;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.sql.CharNameTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * Support for "Chat with Friends" dialog. <br />
 * Add new friend or delete.
 * @author JIV
 */
public class L2Friend implements IClientOutgoingPacket
{
	private final boolean _action;
	private final boolean _online;
	private final int _objid;
	private final String _name;
	
	/**
	 * @param action - true for adding, false for remove
	 * @param objId
	 */
	public L2Friend(boolean action, int objId)
	{
		_action = action;
		_objid = objId;
		_name = CharNameTable.getInstance().getNameById(objId);
		_online = World.getInstance().getPlayer(objId) != null;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.L2_FRIEND.writeId(packet);
		
		packet.writeD(_action ? 1 : 3); // 1-add 3-remove
		packet.writeD(_objid);
		packet.writeS(_name);
		packet.writeD(_online ? 1 : 0);
		packet.writeD(_online ? _objid : 0);
		return true;
	}
}
