package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author JIV
 */
public class ExEnchantSkillResult implements IClientOutgoingPacket
{
	public static final ExEnchantSkillResult STATIC_PACKET_TRUE = new ExEnchantSkillResult(true);
	public static final ExEnchantSkillResult STATIC_PACKET_FALSE = new ExEnchantSkillResult(false);
	
	private final boolean _enchanted;
	
	public ExEnchantSkillResult(boolean enchanted)
	{
		_enchanted = enchanted;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ENCHANT_SKILL_RESULT.writeId(packet);
		
		packet.writeD(_enchanted ? 1 : 0);
		return true;
	}
}
