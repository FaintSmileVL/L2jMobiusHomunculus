package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author devScarlet, mrTJO
 */
public class ServerObjectInfo implements IClientOutgoingPacket
{
	private final Npc _activeChar;
	private final int _x;
	private final int _y;
	private final int _z;
	private final int _heading;
	private final int _displayId;
	private final boolean _isAttackable;
	private final double _collisionHeight;
	private final double _collisionRadius;
	private final String _name;
	
	public ServerObjectInfo(Npc activeChar, Creature actor)
	{
		_activeChar = activeChar;
		_displayId = _activeChar.getTemplate().getDisplayId();
		_isAttackable = _activeChar.isAutoAttackable(actor);
		_collisionHeight = _activeChar.getCollisionHeight();
		_collisionRadius = _activeChar.getCollisionRadius();
		_x = _activeChar.getX();
		_y = _activeChar.getY();
		_z = _activeChar.getZ();
		_heading = _activeChar.getHeading();
		_name = _activeChar.getTemplate().isUsingServerSideName() ? _activeChar.getTemplate().getName() : "";
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SERVER_OBJECT_INFO.writeId(packet);
		
		packet.writeD(_activeChar.getObjectId());
		packet.writeD(_displayId + 1000000);
		packet.writeS(_name); // name
		packet.writeD(_isAttackable ? 1 : 0);
		packet.writeD(_x);
		packet.writeD(_y);
		packet.writeD(_z);
		packet.writeD(_heading);
		packet.writeF(1.0); // movement multiplier
		packet.writeF(1.0); // attack speed multiplier
		packet.writeF(_collisionRadius);
		packet.writeF(_collisionHeight);
		packet.writeD((int) (_isAttackable ? _activeChar.getCurrentHp() : 0));
		packet.writeD(_isAttackable ? _activeChar.getMaxHp() : 0);
		packet.writeD(0x01); // object type
		packet.writeD(0x00); // special effects
		return true;
	}
}
