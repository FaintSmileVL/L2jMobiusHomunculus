package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SurrenderPledgeWar implements IClientOutgoingPacket
{
	private final String _pledgeName;
	private final String _playerName;
	
	public SurrenderPledgeWar(String pledge, String charName)
	{
		_pledgeName = pledge;
		_playerName = charName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SURRENDER_PLEDGE_WAR.writeId(packet);
		
		packet.writeS(_pledgeName);
		packet.writeS(_playerName);
		return true;
	}
}