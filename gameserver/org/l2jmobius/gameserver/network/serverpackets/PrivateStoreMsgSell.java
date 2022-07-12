package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PrivateStoreMsgSell implements IClientOutgoingPacket
{
	private final int _objId;
	private String _storeMsg;
	
	public PrivateStoreMsgSell(PlayerInstance player)
	{
		_objId = player.getObjectId();
		if ((player.getSellList() != null) || player.isSellingBuffs())
		{
			_storeMsg = player.getSellList().getTitle();
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PRIVATE_STORE_MSG.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeS(_storeMsg);
		return true;
	}
}
