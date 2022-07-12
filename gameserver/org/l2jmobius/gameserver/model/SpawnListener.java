package org.l2jmobius.gameserver.model;

import org.l2jmobius.gameserver.model.actor.Npc;

/**
 * @version $Revision: 1.2 $ $Date: 2004/06/27 08:12:59 $
 */
public interface SpawnListener
{
	void npcSpawned(Npc npc);
}
