package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class PrivateStoreMsgBuy implements IClientOutgoingPacket
{
	private final int _objId;
	private String _storeMsg;
	
	public PrivateStoreMsgBuy(PlayerInstance player)
	{
		_objId = player.getObjectId();
		if (player.getBuyList() != null)
		{
			_storeMsg = player.getBuyList().getTitle();
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PRIVATE_STORE_BUY_MSG.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeS(_storeMsg);
		return true;
	}
}
