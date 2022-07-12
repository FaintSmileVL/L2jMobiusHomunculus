package org.l2jmobius.gameserver.model.actor.tasks.attackable;

import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.quest.Quest;

/**
 * @author xban1x
 */
public class OnKillNotifyTask implements Runnable
{
	private final Attackable _attackable;
	private final Quest _quest;
	private final PlayerInstance _killer;
	private final boolean _isSummon;
	
	public OnKillNotifyTask(Attackable attackable, Quest quest, PlayerInstance killer, boolean isSummon)
	{
		_attackable = attackable;
		_quest = quest;
		_killer = killer;
		_isSummon = isSummon;
	}
	
	@Override
	public void run()
	{
		if ((_quest != null) && (_attackable != null) && (_killer != null))
		{
			_quest.notifyKill(_attackable, _killer, _isSummon);
		}
	}
}
