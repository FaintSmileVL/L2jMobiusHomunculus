package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;
import org.l2jmobius.gameserver.network.serverpackets.FakePlayerInfo;
import org.l2jmobius.gameserver.network.serverpackets.NpcInfo;
import org.l2jmobius.gameserver.network.serverpackets.ServerObjectInfo;

public class WaterZone extends ZoneType
{
	public WaterZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		creature.setInsideZone(ZoneId.WATER, true);
		
		// TODO: update to only send speed status when that packet is known
		if (creature.isPlayer())
		{
			final PlayerInstance player = creature.getActingPlayer();
			if (player.checkTransformed(transform -> !transform.canSwim()))
			{
				creature.stopTransformation(true);
			}
			else
			{
				player.broadcastUserInfo();
			}
		}
		else if (creature.isNpc())
		{
			World.getInstance().forEachVisibleObject(creature, PlayerInstance.class, player ->
			{
				if (creature.isFakePlayer())
				{
					player.sendPacket(new FakePlayerInfo((Npc) creature));
				}
				else if (creature.getRunSpeed() == 0)
				{
					player.sendPacket(new ServerObjectInfo((Npc) creature, player));
				}
				else
				{
					player.sendPacket(new NpcInfo((Npc) creature));
				}
			});
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		creature.setInsideZone(ZoneId.WATER, false);
		
		// TODO: update to only send speed status when that packet is known
		if (creature.isPlayer())
		{
			// Mobius: Attempt to stop water task.
			if (!creature.isInsideZone(ZoneId.WATER))
			{
				((PlayerInstance) creature).stopWaterTask();
			}
			if (!creature.isTeleporting())
			{
				creature.getActingPlayer().broadcastUserInfo();
			}
		}
		else if (creature.isNpc())
		{
			World.getInstance().forEachVisibleObject(creature, PlayerInstance.class, player ->
			{
				if (creature.isFakePlayer())
				{
					player.sendPacket(new FakePlayerInfo((Npc) creature));
				}
				else if (creature.getRunSpeed() == 0)
				{
					player.sendPacket(new ServerObjectInfo((Npc) creature, player));
				}
				else
				{
					player.sendPacket(new NpcInfo((Npc) creature));
				}
			});
		}
	}
	
	public int getWaterZ()
	{
		return getZone().getHighZ();
	}
}
