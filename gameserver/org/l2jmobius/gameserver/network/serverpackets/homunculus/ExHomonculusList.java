package org.l2jmobius.gameserver.network.serverpackets.homunculus;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExHomonculusList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public ExHomonculusList(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_HOMUNCULUS_LIST.writeId(packet);
		
		final int homunculus = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_ID, 0);
		if (homunculus > 0)
		{
			final int quality = _player.getVariables().getInt(PlayerVariables.HOMUNCULUS_QUALITY, 0);
			
			packet.writeD(0x01); // Size.
			
			packet.writeD(0x00); // nIdx
			packet.writeD(homunculus); // nID
			packet.writeD(quality); // eType
			packet.writeC(0x01); // bActivate
			
			packet.writeD(0x00); // m_nSkillID 0
			packet.writeD(0x00); // m_nSkillID 1
			packet.writeD(0x00); // m_nSkillID 2
			packet.writeD(0x00); // m_nSkillID 3
			packet.writeD(0x00); // m_nSkillID 4
			packet.writeD(0x00); // m_nSkillID 5
			
			packet.writeD(0x00); // m_nSkillLevel 0
			packet.writeD(0x00); // m_nSkillLevel 1
			packet.writeD(0x00); // m_nSkillLevel 2
			packet.writeD(0x00); // m_nSkillLevel 3
			packet.writeD(0x00); // m_nSkillLevel 4
			packet.writeD(0x00); // m_nSkillLevel 5
			
			packet.writeD(0x01); // m_nLevel
			packet.writeD(0x00); // m_nExp
			packet.writeD(_player.getHomunculusHpBonus()); // m_nHP
			packet.writeD(_player.getHomunculusAtkBonus()); // m_nAttack
			packet.writeD(_player.getHomunculusDefBonus()); // m_nDefence
			packet.writeD((int) (_player.getHomunculusCritBonus() * 100)); // m_nCritical
		}
		else
		{
			packet.writeD(0); // Size.
		}
		
		return true;
	}
}
