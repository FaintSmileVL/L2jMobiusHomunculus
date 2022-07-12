package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

// This class is here mostly for convenience and for avoidance of hardcoded IDs.
// It refers to Beast (mobs) that can be attacked but can also be fed
// For example, the Beast Farm's Alpen Buffalo.
// This class is only truly used by the handlers in order to check the correctness
// of the target.  However, no additional tasks are needed, since they are all
// handled by scripted AI.
public class FeedableBeastInstance extends MonsterInstance
{
	public FeedableBeastInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.FeedableBeastInstance);
	}
}
