package org.l2jmobius.gameserver.model.skills;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NosBit
 */
public enum SkillConditionScope
{
	GENERAL("conditions"),
	TARGET("targetConditions"),
	PASSIVE("passiveConditions");
	
	private static final Map<String, SkillConditionScope> XML_NODE_NAME_TO_SKILL_CONDITION_SCOPE;
	
	static
	{
		XML_NODE_NAME_TO_SKILL_CONDITION_SCOPE = Arrays.stream(values()).collect(Collectors.toMap(e -> e.getXmlNodeName(), e -> e));
	}
	
	private final String _xmlNodeName;
	
	SkillConditionScope(String xmlNodeName)
	{
		_xmlNodeName = xmlNodeName;
	}
	
	public String getXmlNodeName()
	{
		return _xmlNodeName;
	}
	
	public static SkillConditionScope findByXmlNodeName(String xmlNodeName)
	{
		return XML_NODE_NAME_TO_SKILL_CONDITION_SCOPE.get(xmlNodeName);
	}
}
