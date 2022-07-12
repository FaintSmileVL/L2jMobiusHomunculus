package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Shortcut;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShortCutInit implements IClientOutgoingPacket
{
	private Shortcut[] _shortCuts;
	
	public ShortCutInit(PlayerInstance player)
	{
		if (player == null)
		{
			return;
		}
		
		_shortCuts = player.getAllShortCuts();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHORT_CUT_INIT.writeId(packet);
		
		packet.writeD(_shortCuts.length);
		for (Shortcut sc : _shortCuts)
		{
			packet.writeD(sc.getType().ordinal());
			packet.writeD(sc.getSlot() + (sc.getPage() * 12));
			
			packet.writeC(0x00); // 228
			
			switch (sc.getType())
			{
				case ITEM:
				{
					packet.writeD(sc.getId());
					packet.writeD(0x01); // Enabled or not
					packet.writeD(sc.getSharedReuseGroup());
					packet.writeD(0x00);
					packet.writeD(0x00);
					packet.writeQ(0x00); // Augment id
					packet.writeD(0x00); // Visual id
					break;
				}
				case SKILL:
				{
					packet.writeD(sc.getId());
					packet.writeH(sc.getLevel());
					packet.writeH(sc.getSubLevel());
					packet.writeD(sc.getSharedReuseGroup());
					packet.writeC(0x00); // C5
					packet.writeD(0x01); // C6
					break;
				}
				case ACTION:
				case MACRO:
				case RECIPE:
				case BOOKMARK:
				{
					packet.writeD(sc.getId());
					packet.writeD(0x01); // C6
				}
			}
		}
		return true;
	}
}
