package org.l2jmobius.gameserver.model.actor.request;

import java.util.Objects;

import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public class PartyRequest extends AbstractRequest
{
	private final PlayerInstance _targetPlayer;
	private final Party _party;
	
	public PartyRequest(PlayerInstance player, PlayerInstance targetPlayer, Party party)
	{
		super(player);
		Objects.requireNonNull(targetPlayer);
		Objects.requireNonNull(party);
		_targetPlayer = targetPlayer;
		_party = party;
	}
	
	public PlayerInstance getTargetPlayer()
	{
		return _targetPlayer;
	}
	
	public Party getParty()
	{
		return _party;
	}
	
	@Override
	public boolean isUsing(int objectId)
	{
		return false;
	}
	
	@Override
	public void onTimeout()
	{
		super.onTimeout();
		getActiveChar().removeRequest(getClass());
		_targetPlayer.removeRequest(getClass());
	}
}
