package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExShowChannelingEffect extends AbstractItemPacket
{
	private final Creature _caster;
	private final Creature _target;
	private final int _state;
	
	public ExShowChannelingEffect(Creature caster, Creature target, int state)
	{
		_caster = caster;
		_target = target;
		_state = state;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_CHANNELING_EFFECT.writeId(packet);
		packet.writeD(_caster.getObjectId());
		packet.writeD(_target.getObjectId());
		packet.writeD(_state);
		return true;
	}
}
