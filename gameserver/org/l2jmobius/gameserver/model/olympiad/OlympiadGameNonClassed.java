package org.l2jmobius.gameserver.model.olympiad;

import java.util.Set;

import org.l2jmobius.Config;

/**
 * @author DS
 */
public class OlympiadGameNonClassed extends OlympiadGameNormal
{
	public OlympiadGameNonClassed(int id, Participant[] opponents)
	{
		super(id, opponents);
	}
	
	@Override
	public CompetitionType getType()
	{
		return CompetitionType.NON_CLASSED;
	}
	
	@Override
	protected int getDivider()
	{
		return Config.ALT_OLY_DIVIDER_NON_CLASSED;
	}
	
	protected static OlympiadGameNonClassed createGame(int id, Set<Integer> list)
	{
		final Participant[] opponents = OlympiadGameNormal.createListOfParticipants(list);
		if (opponents == null)
		{
			return null;
		}
		return new OlympiadGameNonClassed(id, opponents);
	}
}
