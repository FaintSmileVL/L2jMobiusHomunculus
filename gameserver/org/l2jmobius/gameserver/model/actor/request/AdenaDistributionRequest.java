package org.l2jmobius.gameserver.model.actor.request;

import java.util.List;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.serverpackets.adenadistribution.ExDivideAdenaCancel;

/**
 * @author Sdw
 */
public class AdenaDistributionRequest extends AbstractRequest
{
	private final PlayerInstance _distributor;
	private final List<PlayerInstance> _players;
	private final int _adenaObjectId;
	private final long _adenaCount;
	
	public AdenaDistributionRequest(PlayerInstance player, PlayerInstance distributor, List<PlayerInstance> players, int adenaObjectId, long adenaCount)
	{
		super(player);
		_distributor = distributor;
		_adenaObjectId = adenaObjectId;
		_players = players;
		_adenaCount = adenaCount;
	}
	
	public PlayerInstance getDistributor()
	{
		return _distributor;
	}
	
	public List<PlayerInstance> getPlayers()
	{
		return _players;
	}
	
	public int getAdenaObjectId()
	{
		return _adenaObjectId;
	}
	
	public long getAdenaCount()
	{
		return _adenaCount;
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		return objectId == _adenaObjectId;
	}
	
	@Override
	public void onTimeout()
	{
		super.onTimeout();
		_players.forEach(p ->
		{
			p.removeRequest(AdenaDistributionRequest.class);
			p.sendPacket(ExDivideAdenaCancel.STATIC_PACKET);
		});
	}
}
