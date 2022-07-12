package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.enums.Race;
import org.l2jmobius.gameserver.enums.Sex;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.beautyshop.BeautyData;
import org.l2jmobius.gameserver.model.beautyshop.BeautyItem;

/**
 * @author Sdw
 */
public class BeautyShopData implements IXmlReader
{
	private final Map<Race, Map<Sex, BeautyData>> _beautyList = new EnumMap<>(Race.class);
	private final Map<Sex, BeautyData> _beautyData = new EnumMap<>(Sex.class);
	
	protected BeautyShopData()
	{
		load();
	}
	
	@Override
	public synchronized void load()
	{
		_beautyList.clear();
		_beautyData.clear();
		parseDatapackFile("data/BeautyShop.xml");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		NamedNodeMap attrs;
		StatSet set;
		Node att;
		Race race = null;
		Sex sex = null;
		for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("race".equalsIgnoreCase(d.getNodeName()))
					{
						att = d.getAttributes().getNamedItem("type");
						if (att != null)
						{
							race = parseEnum(att, Race.class);
						}
						
						for (Node b = d.getFirstChild(); b != null; b = b.getNextSibling())
						{
							if ("sex".equalsIgnoreCase(b.getNodeName()))
							{
								att = b.getAttributes().getNamedItem("type");
								if (att != null)
								{
									sex = parseEnum(att, Sex.class);
								}
								
								final BeautyData beautyData = new BeautyData();
								for (Node a = b.getFirstChild(); a != null; a = a.getNextSibling())
								{
									if ("hair".equalsIgnoreCase(a.getNodeName()))
									{
										attrs = a.getAttributes();
										set = new StatSet();
										for (int i = 0; i < attrs.getLength(); i++)
										{
											att = attrs.item(i);
											set.set(att.getNodeName(), att.getNodeValue());
										}
										final BeautyItem hair = new BeautyItem(set);
										for (Node g = a.getFirstChild(); g != null; g = g.getNextSibling())
										{
											if ("color".equalsIgnoreCase(g.getNodeName()))
											{
												attrs = g.getAttributes();
												set = new StatSet();
												for (int i = 0; i < attrs.getLength(); i++)
												{
													att = attrs.item(i);
													set.set(att.getNodeName(), att.getNodeValue());
												}
												hair.addColor(set);
											}
										}
										beautyData.addHair(hair);
									}
									else if ("face".equalsIgnoreCase(a.getNodeName()))
									{
										attrs = a.getAttributes();
										set = new StatSet();
										for (int i = 0; i < attrs.getLength(); i++)
										{
											att = attrs.item(i);
											set.set(att.getNodeName(), att.getNodeValue());
										}
										final BeautyItem face = new BeautyItem(set);
										beautyData.addFace(face);
									}
								}
								
								_beautyData.put(sex, beautyData);
							}
						}
						_beautyList.put(race, _beautyData);
					}
				}
			}
		}
	}
	
	public boolean hasBeautyData(Race race, Sex sex)
	{
		return _beautyList.containsKey(race) && _beautyList.get(race).containsKey(sex);
	}
	
	public BeautyData getBeautyData(Race race, Sex sex)
	{
		if (_beautyList.containsKey(race))
		{
			return _beautyList.get(race).get(sex);
		}
		return null;
	}
	
	public static BeautyShopData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final BeautyShopData INSTANCE = new BeautyShopData();
	}
}
