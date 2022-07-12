package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * Special Camera server packet implementation.
 * @author Zoey76
 */
public class SpecialCamera implements IClientOutgoingPacket
{
	private final int _id;
	private final int _force;
	private final int _angle1;
	private final int _angle2;
	private final int _time;
	private final int _duration;
	private final int _relYaw;
	private final int _relPitch;
	private final int _isWide;
	private final int _relAngle;
	private final int _unk;
	
	/**
	 * Special Camera packet constructor.
	 * @param creature the creature
	 * @param force
	 * @param angle1
	 * @param angle2
	 * @param time
	 * @param range
	 * @param duration
	 * @param relYaw
	 * @param relPitch
	 * @param isWide
	 * @param relAngle
	 */
	public SpecialCamera(Creature creature, int force, int angle1, int angle2, int time, int range, int duration, int relYaw, int relPitch, int isWide, int relAngle)
	{
		this(creature, force, angle1, angle2, time, duration, range, relYaw, relPitch, isWide, relAngle, 0);
	}
	
	/**
	 * Special Camera Ex packet constructor.
	 * @param creature the creature
	 * @param talker
	 * @param force
	 * @param angle1
	 * @param angle2
	 * @param time
	 * @param duration
	 * @param relYaw
	 * @param relPitch
	 * @param isWide
	 * @param relAngle
	 */
	public SpecialCamera(Creature creature, Creature talker, int force, int angle1, int angle2, int time, int duration, int relYaw, int relPitch, int isWide, int relAngle)
	{
		this(creature, force, angle1, angle2, time, duration, 0, relYaw, relPitch, isWide, relAngle, 0);
	}
	
	/**
	 * Special Camera 3 packet constructor.
	 * @param creature the creature
	 * @param force
	 * @param angle1
	 * @param angle2
	 * @param time
	 * @param range
	 * @param duration
	 * @param relYaw
	 * @param relPitch
	 * @param isWide
	 * @param relAngle
	 * @param unk unknown post-C4 parameter
	 */
	public SpecialCamera(Creature creature, int force, int angle1, int angle2, int time, int range, int duration, int relYaw, int relPitch, int isWide, int relAngle, int unk)
	{
		_id = creature.getObjectId();
		_force = force;
		_angle1 = angle1;
		_angle2 = angle2;
		_time = time;
		_duration = duration;
		_relYaw = relYaw;
		_relPitch = relPitch;
		_isWide = isWide;
		_relAngle = relAngle;
		_unk = unk;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.SPECIAL_CAMERA.writeId(packet);
		
		packet.writeD(_id);
		packet.writeD(_force);
		packet.writeD(_angle1);
		packet.writeD(_angle2);
		packet.writeD(_time);
		packet.writeD(_duration);
		packet.writeD(_relYaw);
		packet.writeD(_relPitch);
		packet.writeD(_isWide);
		packet.writeD(_relAngle);
		packet.writeD(_unk);
		return true;
	}
}
