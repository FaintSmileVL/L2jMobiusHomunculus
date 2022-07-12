package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO & UnAfraid
 */
public class ExConfirmAddingContact implements IClientOutgoingPacket
{
	private final String _charName;
	private final boolean _added;
	
	public ExConfirmAddingContact(String charName, boolean added)
	{
		_charName = charName;
		_added = added;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_AGIT_AUCTION_CMD.writeId(packet);
		
		packet.writeS(_charName);
		packet.writeD(_added ? 0x01 : 0x00);
		return true;
	}
}
