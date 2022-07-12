package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.skills.AbnormalVisualEffect;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class NpcInfoAbnormalVisualEffect implements IClientOutgoingPacket
{
	private final Npc _npc;
	
	public NpcInfoAbnormalVisualEffect(Npc npc)
	{
		_npc = npc;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.NPC_INFO_ABNORMAL_VISUAL_EFFECT.writeId(packet);
		
		packet.writeD(_npc.getObjectId());
		packet.writeD(_npc.getTransformationDisplayId());
		
		final Set<AbnormalVisualEffect> abnormalVisualEffects = _npc.getEffectList().getCurrentAbnormalVisualEffects();
		packet.writeD(abnormalVisualEffects.size());
		for (AbnormalVisualEffect abnormalVisualEffect : abnormalVisualEffects)
		{
			packet.writeH(abnormalVisualEffect.getClientId());
		}
		return true;
	}
}
