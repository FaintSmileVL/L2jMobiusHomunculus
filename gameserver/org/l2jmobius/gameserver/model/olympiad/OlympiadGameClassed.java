package org.l2jmobius.gameserver.model.olympiad;

import java.util.List;
import java.util.Set;

import org.l2jmobius.Config;
import util.Rnd;

/**
 * @author DS
 */
public class OlympiadGameClassed extends OlympiadGameNormal
{
	private OlympiadGameClassed(int id, Participant[] opponents)
	{
		super(id, opponents);
	}
	
	@Override
	public CompetitionType getType()
	{
		return CompetitionType.CLASSED;
	}
	
	@Override
	protected int getDivider()
	{
		return Config.ALT_OLY_DIVIDER_CLASSED;
	}
	
	protected static OlympiadGameClassed createGame(int id, List<Set<Integer>> classList)
	{
		if ((classList == null) || classList.isEmpty())
		{
			return null;
		}
		
		Set<Integer> list;
		Participant[] opponents;
		while (!classList.isEmpty())
		{
			list = classList.get(Rnd.get(classList.size()));
			if ((list == null) || (list.size() < 2))
			{
				classList.remove(list);
				continue;
			}
			
			opponents = OlympiadGameNormal.createListOfParticipants(list);
			if (opponents == null)
			{
				classList.remove(list);
				continue;
			}
			
			return new OlympiadGameClassed(id, opponents);
		}
		return null;
	}
}