package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class AbnormalStatusUpdate implements IClientOutgoingPacket
{
	private final List<BuffInfo> _effects = new ArrayList<>();
	
	public void addSkill(BuffInfo info)
	{
		if (!info.getSkill().isHealingPotionSkill())
		{
			_effects.add(info);
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ABNORMAL_STATUS_UPDATE.writeId(packet);
		
		packet.writeH(_effects.size());
		for (BuffInfo info : _effects)
		{
			if ((info != null) && info.isInUse())
			{
				packet.writeD(info.getSkill().getDisplayId());
				packet.writeH(info.getSkill().getDisplayLevel());
				packet.writeH(info.getSkill().getSubLevel());
				packet.writeD(info.getSkill().getAbnormalType().getClientId());
				writeOptionalD(packet, info.getSkill().isAura() || info.getSkill().isToggle() ? -1 : info.getTime());
			}
		}
		return true;
	}
}
