package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.skills.SkillCastingType;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * MagicSkillLaunched server packet implementation.
 * @author UnAfraid
 */
public class MagicSkillLaunched implements IClientOutgoingPacket
{
	private final int _objectId;
	private final int _skillId;
	private final int _skillLevel;
	private final SkillCastingType _castingType;
	private final Collection<WorldObject> _targets;
	
	public MagicSkillLaunched(Creature creature, int skillId, int skillLevel, SkillCastingType castingType, Collection<WorldObject> targets)
	{
		_objectId = creature.getObjectId();
		_skillId = skillId;
		_skillLevel = skillLevel;
		_castingType = castingType;
		if (targets == null)
		{
			_targets = Collections.singletonList(creature);
			return;
		}
		_targets = targets;
	}
	
	public MagicSkillLaunched(Creature creature, int skillId, int skillLevel, SkillCastingType castingType, WorldObject... targets)
	{
		this(creature, skillId, skillLevel, castingType, (targets == null ? Collections.singletonList(creature) : Arrays.asList(targets)));
	}
	
	public MagicSkillLaunched(Creature creature, int skillId, int skillLevel)
	{
		this(creature, skillId, skillLevel, SkillCastingType.NORMAL, Collections.singletonList(creature));
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MAGIC_SKILL_LAUNCHED.writeId(packet);
		
		packet.writeD(_castingType.getClientBarId()); // MagicSkillUse castingType
		packet.writeD(_objectId);
		packet.writeD(_skillId);
		packet.writeD(_skillLevel);
		packet.writeD(_targets.size());
		for (WorldObject target : _targets)
		{
			packet.writeD(target.getObjectId());
		}
		return true;
	}
}
