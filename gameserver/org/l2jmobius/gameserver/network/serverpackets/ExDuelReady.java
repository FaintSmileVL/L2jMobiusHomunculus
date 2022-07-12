package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class ExDuelReady implements IClientOutgoingPacket
{
	public static final ExDuelReady PLAYER_DUEL = new ExDuelReady(false);
	public static final ExDuelReady PARTY_DUEL = new ExDuelReady(true);
	
	private final int _partyDuel;
	
	public ExDuelReady(boolean isPartyDuel)
	{
		_partyDuel = isPartyDuel ? 1 : 0;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DUEL_READY.writeId(packet);
		
		packet.writeD(_partyDuel);
		return true;
	}
}
