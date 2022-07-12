package org.l2jmobius.gameserver.network.serverpackets.faction;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.Faction;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mathael, Mobius
 */
public class ExFactionInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final boolean _openDialog;
	
	public ExFactionInfo(PlayerInstance player, boolean openDialog)
	{
		_player = player;
		_openDialog = openDialog;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_FACTION_INFO.writeId(packet);
		
		packet.writeD(_player.getObjectId());
		packet.writeC(_openDialog ? 1 : 0);
		packet.writeD(Faction.values().length);
		
		for (Faction faction : Faction.values())
		{
			packet.writeC(faction.getId());
			packet.writeH(_player.getFactionLevel(faction));
			packet.writeE(_player.getFactionProgress(faction));
		}
		return true;
	}
}
