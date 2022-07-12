package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.Shortcut;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class ShortCutRegister implements IClientOutgoingPacket
{
	private final Shortcut _shortcut;
	
	/**
	 * Register new skill shortcut
	 * @param shortcut
	 */
	public ShortCutRegister(Shortcut shortcut)
	{
		_shortcut = shortcut;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SHORT_CUT_REGISTER.writeId(packet);
		
		packet.writeD(_shortcut.getType().ordinal());
		packet.writeD(_shortcut.getSlot() + (_shortcut.getPage() * 12)); // C4 Client
		
		packet.writeC(0x00); // 228
		
		switch (_shortcut.getType())
		{
			case ITEM:
			{
				packet.writeD(_shortcut.getId());
				packet.writeD(_shortcut.getCharacterType());
				packet.writeD(_shortcut.getSharedReuseGroup());
				packet.writeD(0x00); // unknown
				packet.writeD(0x00); // unknown
				packet.writeD(0x00); // item augment id
				packet.writeD(0x00); // TODO: Find me, item visual id ?
				break;
			}
			case SKILL:
			{
				packet.writeD(_shortcut.getId());
				packet.writeH(_shortcut.getLevel());
				packet.writeH(_shortcut.getSubLevel());
				packet.writeD(_shortcut.getSharedReuseGroup());
				packet.writeC(0x00); // C5
				packet.writeD(_shortcut.getCharacterType());
				packet.writeD(0x00); // TODO: Find me
				packet.writeD(0x00); // TODO: Find me
				break;
			}
			case ACTION:
			case MACRO:
			case RECIPE:
			case BOOKMARK:
			{
				packet.writeD(_shortcut.getId());
				packet.writeD(_shortcut.getCharacterType());
				break;
			}
		}
		return true;
	}
}
