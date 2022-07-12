package org.l2jmobius.gameserver.model.olympiad;

import org.l2jmobius.gameserver.data.SpawnTable;
import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.model.Spawn;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.NpcStringId;

/**
 * @author DS
 */
public class OlympiadAnnouncer implements Runnable
{
	private static final int OLY_MANAGER = 31688;
	
	private int _currentStadium = 0;
	
	@Override
	public void run()
	{
		OlympiadGameTask task;
		for (int i = OlympiadGameManager.getInstance().getNumberOfStadiums(); --i >= 0; _currentStadium++)
		{
			if (_currentStadium >= OlympiadGameManager.getInstance().getNumberOfStadiums())
			{
				_currentStadium = 0;
			}
			
			task = OlympiadGameManager.getInstance().getOlympiadTask(_currentStadium);
			if ((task != null) && (task.getGame() != null) && task.needAnnounce())
			{
				NpcStringId npcString;
				final String arenaId = String.valueOf(task.getGame().getStadiumId() + 1);
				switch (task.getGame().getType())
				{
					case NON_CLASSED:
					{
						npcString = NpcStringId.THE_WORLD_OLYMPIAD_WILL_BEGIN_IN_ARENA_S1_SOON;
						break;
					}
					case CLASSED:
					{
						npcString = NpcStringId.OLYMPIAD_CLASS_INDIVIDUAL_MATCH_IS_GOING_TO_BEGIN_IN_ARENA_S1_IN_A_MOMENT;
						break;
					}
					default:
					{
						continue;
					}
				}
				
				for (Spawn spawn : SpawnTable.getInstance().getSpawns(OLY_MANAGER))
				{
					final Npc manager = spawn.getLastSpawn();
					if (manager != null)
					{
						manager.broadcastSay(ChatType.NPC_SHOUT, npcString, arenaId);
					}
				}
				break;
			}
		}
	}
}
