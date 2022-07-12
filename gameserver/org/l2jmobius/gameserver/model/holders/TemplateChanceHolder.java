package org.l2jmobius.gameserver.model.holders;

/**
 * An object for holding template id and chance
 * @author Nik
 */
public class TemplateChanceHolder
{
	private final int _templateId;
	private final int _minChance;
	private final int _maxChance;
	
	public TemplateChanceHolder(int templateId, int minChance, int maxChance)
	{
		_templateId = templateId;
		_minChance = minChance;
		_maxChance = maxChance;
	}
	
	public int getTemplateId()
	{
		return _templateId;
	}
	
	public boolean calcChance(int chance)
	{
		return (_maxChance > chance) && (chance >= _minChance);
	}
	
	@Override
	public String toString()
	{
		return "[TemplateId: " + _templateId + " minChance: " + _minChance + " maxChance: " + _minChance + "]";
	}
}