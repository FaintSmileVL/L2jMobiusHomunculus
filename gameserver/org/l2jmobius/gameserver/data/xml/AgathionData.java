package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.w3c.dom.Document;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.holders.AgathionSkillHolder;
import org.l2jmobius.gameserver.model.holders.SkillHolder;

/**
 * @author Mobius
 */
public class AgathionData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(AgathionData.class.getName());
	
	private static final Map<Integer, AgathionSkillHolder> AGATHION_SKILLS = new HashMap<>();
	
	protected AgathionData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		AGATHION_SKILLS.clear();
		parseDatapackFile("data/AgathionData.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + AGATHION_SKILLS.size() + " agathion data.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "agathion", agathionNode ->
		{
			final StatSet set = new StatSet(parseAttributes(agathionNode));
			
			final int id = set.getInt("id");
			if (ItemTable.getInstance().getTemplate(id) == null)
			{
				LOGGER.info(getClass().getSimpleName() + ": Could not find agathion with id " + id + ".");
				return;
			}
			
			final int enchant = set.getInt("enchant", 0);
			
			final Map<Integer, List<SkillHolder>> mainSkills = AGATHION_SKILLS.containsKey(id) ? AGATHION_SKILLS.get(id).getMainSkills() : new HashMap<>();
			final List<SkillHolder> mainSkillList = new ArrayList<>();
			final String main = set.getString("mainSkill", "");
			for (String skill : main.split(";"))
			{
				if (skill.isEmpty())
				{
					continue;
				}
				
				final String[] split = skill.split(",");
				final int skillId = Integer.parseInt(split[0]);
				final int level = Integer.parseInt(split[1]);
				if (SkillData.getInstance().getSkill(skillId, level) == null)
				{
					LOGGER.info(getClass().getSimpleName() + ": Could not find agathion skill id " + skillId + ".");
					return;
				}
				
				mainSkillList.add(new SkillHolder(skillId, level));
			}
			mainSkills.put(enchant, mainSkillList);
			
			final Map<Integer, List<SkillHolder>> subSkills = AGATHION_SKILLS.containsKey(id) ? AGATHION_SKILLS.get(id).getSubSkills() : new HashMap<>();
			final List<SkillHolder> subSkillList = new ArrayList<>();
			final String sub = set.getString("subSkill", "");
			for (String skill : sub.split(";"))
			{
				if (skill.isEmpty())
				{
					continue;
				}
				
				final String[] split = skill.split(",");
				final int skillId = Integer.parseInt(split[0]);
				final int level = Integer.parseInt(split[1]);
				if (SkillData.getInstance().getSkill(skillId, level) == null)
				{
					LOGGER.info(getClass().getSimpleName() + ": Could not find agathion skill id " + skillId + ".");
					return;
				}
				
				subSkillList.add(new SkillHolder(skillId, level));
			}
			subSkills.put(enchant, subSkillList);
			
			AGATHION_SKILLS.put(id, new AgathionSkillHolder(mainSkills, subSkills));
		}));
	}
	
	public AgathionSkillHolder getSkills(int agathionId)
	{
		return AGATHION_SKILLS.get(agathionId);
	}
	
	public static AgathionData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final AgathionData INSTANCE = new AgathionData();
	}
}
