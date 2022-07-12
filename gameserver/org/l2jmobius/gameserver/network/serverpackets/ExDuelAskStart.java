package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExDuelAskStart implements IClientOutgoingPacket
{
	private final String _requestorName;
	private final int _partyDuel;
	
	public ExDuelAskStart(String requestor, int partyDuel)
	{
		_requestorName = requestor;
		_partyDuel = partyDuel;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DUEL_ASK_START.writeId(packet);
		
		packet.writeS(_requestorName);
		packet.writeD(_partyDuel);
		return true;
	}
}
