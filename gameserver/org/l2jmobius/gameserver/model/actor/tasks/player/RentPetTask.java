package org.l2jmobius.gameserver.model.actor.tasks.player;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * Task dedicated to dismount player from rented pet.
 * @author UnAfraid
 */
public class RentPetTask implements Runnable
{
	private final PlayerInstance _player;
	
	public RentPetTask(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public void run()
	{
		if (_player != null)
		{
			_player.stopRentPet();
		}
	}
}
