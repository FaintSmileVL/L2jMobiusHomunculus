package org.l2jmobius.gameserver.network.clientpackets.raidbossinfo;

import java.util.HashMap;
import java.util.Map;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.DBSpawnManager;
import org.l2jmobius.gameserver.instancemanager.GrandBossManager;
import org.l2jmobius.gameserver.model.actor.instance.GrandBossInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.raidbossinfo.ExRaidBossSpawnInfo;

/**
 * @author Mobius
 */
public class RequestRaidBossSpawnInfo implements IClientIncomingPacket
{
	private final Map<Integer, Integer> _statuses = new HashMap<>();
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int count = packet.readD();
		for (int i = 0; i < count; i++)
		{
			final int bossId = packet.readD();
			final GrandBossInstance boss = GrandBossManager.getInstance().getBoss(bossId);
			if (boss == null)
			{
				final int status = DBSpawnManager.getInstance().getNpcStatusId(bossId).ordinal();
				if (status != 3)
				{
					_statuses.put(bossId, status);
				}
				else
				{
					// LOGGER.warning("Could not find spawn info for boss " + bossId + ".");
				}
			}
			else
			{
				if (boss.isDead() || !boss.isSpawned())
				{
					_statuses.put(bossId, 0);
				}
				else if (boss.isInCombat())
				{
					_statuses.put(bossId, 2);
				}
				else
				{
					_statuses.put(bossId, 1);
				}
			}
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		client.sendPacket(new ExRaidBossSpawnInfo(_statuses));
	}
}
