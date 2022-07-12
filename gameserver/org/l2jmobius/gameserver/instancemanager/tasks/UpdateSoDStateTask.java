package org.l2jmobius.gameserver.instancemanager.tasks;

import org.l2jmobius.gameserver.instancemanager.GraciaSeedsManager;

/**
 * Task which updates Seed of Destruction state.
 * @author xban1x
 */
public class UpdateSoDStateTask implements Runnable
{
	@Override
	public void run()
	{
		final GraciaSeedsManager manager = GraciaSeedsManager.getInstance();
		manager.setSoDState(1, true);
		manager.updateSodState();
	}
}
