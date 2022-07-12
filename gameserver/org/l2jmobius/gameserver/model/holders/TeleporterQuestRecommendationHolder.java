package org.l2jmobius.gameserver.model.holders;

/**
 * @author Mobius
 */
public class TeleporterQuestRecommendationHolder
{
	private final int _npcId;
	private final String _questName;
	private final int[] _conditions; // -1 = all conditions
	private final String _html;
	
	public TeleporterQuestRecommendationHolder(int npcId, String questName, int[] conditions, String html)
	{
		_npcId = npcId;
		_questName = questName;
		_conditions = conditions;
		_html = html;
	}
	
	public int getNpcId()
	{
		return _npcId;
	}
	
	public String getQuestName()
	{
		return _questName;
	}
	
	public int[] getConditions()
	{
		return _conditions;
	}
	
	public String getHtml()
	{
		return _html;
	}
}