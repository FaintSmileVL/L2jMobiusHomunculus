package org.l2jmobius.gameserver.model.actor.request;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public class PrimeShopRequest extends AbstractRequest
{
	public PrimeShopRequest(PlayerInstance player)
	{
		super(player);
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		return false;
	}
}
