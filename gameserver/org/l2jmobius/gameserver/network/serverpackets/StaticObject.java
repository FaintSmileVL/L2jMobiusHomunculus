package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.DoorInstance;
import org.l2jmobius.gameserver.model.actor.instance.StaticObjectInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author KenM
 */
public class StaticObject implements IClientOutgoingPacket
{
	private final int _staticObjectId;
	private final int _objectId;
	private final int _type;
	private final boolean _isTargetable;
	private final int _meshIndex;
	private final boolean _isClosed;
	private final boolean _isEnemy;
	private final int _maxHp;
	private final int _currentHp;
	private final boolean _showHp;
	private final int _damageGrade;
	
	public StaticObject(StaticObjectInstance staticObject)
	{
		_staticObjectId = staticObject.getId();
		_objectId = staticObject.getObjectId();
		_type = 0;
		_isTargetable = true;
		_meshIndex = staticObject.getMeshIndex();
		_isClosed = false;
		_isEnemy = false;
		_maxHp = 0;
		_currentHp = 0;
		_showHp = false;
		_damageGrade = 0;
	}
	
	public StaticObject(DoorInstance door, boolean targetable)
	{
		_staticObjectId = door.getId();
		_objectId = door.getObjectId();
		_type = 1;
		_isTargetable = door.isTargetable() || targetable;
		_meshIndex = door.getMeshIndex();
		_isClosed = !door.isOpen();
		_isEnemy = door.isEnemy();
		_maxHp = door.getMaxHp();
		_currentHp = (int) door.getCurrentHp();
		_showHp = door.isShowHp();
		_damageGrade = door.getDamage();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.STATIC_OBJECT.writeId(packet);
		
		packet.writeD(_staticObjectId);
		packet.writeD(_objectId);
		packet.writeD(_type);
		packet.writeD(_isTargetable ? 1 : 0);
		packet.writeD(_meshIndex);
		packet.writeD(_isClosed ? 1 : 0);
		packet.writeD(_isEnemy ? 1 : 0);
		packet.writeD(_currentHp);
		packet.writeD(_maxHp);
		packet.writeD(_showHp ? 1 : 0);
		packet.writeD(_damageGrade);
		return true;
	}
}
