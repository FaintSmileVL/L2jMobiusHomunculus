package org.l2jmobius.gameserver.model.events.impl.ceremonyofchaos;

import java.util.List;

import org.l2jmobius.gameserver.model.ceremonyofchaos.CeremonyOfChaosMember;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;

/**
 * @author UnAfraid
 */
public class OnCeremonyOfChaosMatchResult implements IBaseEvent
{
	private final List<CeremonyOfChaosMember> _winners;
	private final List<CeremonyOfChaosMember> _members;
	
	public OnCeremonyOfChaosMatchResult(List<CeremonyOfChaosMember> winners, List<CeremonyOfChaosMember> members)
	{
		_winners = winners;
		_members = members;
	}
	
	public List<CeremonyOfChaosMember> getWinners()
	{
		return _winners;
	}
	
	public List<CeremonyOfChaosMember> getMembers()
	{
		return _members;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_CEREMONY_OF_CHAOS_MATCH_RESULT;
	}
}