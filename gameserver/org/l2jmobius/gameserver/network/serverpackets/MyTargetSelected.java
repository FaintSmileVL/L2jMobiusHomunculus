package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.ControllableAirShipInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * MyTargetSelected server packet implementation.
 * @author UnAfraid
 */
public class MyTargetSelected implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _color;
	
	/**
	 * @param player
	 * @param target
	 */
	public MyTargetSelected(PlayerInstance player, Creature target)
	{
		_objectId = (target instanceof ControllableAirShipInstance) ? ((ControllableAirShipInstance) target).getHelmObjectId() : target.getObjectId();
		_color = target.isAutoAttackable(player) ? (player.getLevel() - target.getLevel()) : 0;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MY_TARGET_SELECTED.writeId(packet);
		
		packet.writeD(0x01); // Grand Crusade
		packet.writeD(_objectId);
		packet.writeH(_color);
		packet.writeD(0x00);
		return true;
	}
}
