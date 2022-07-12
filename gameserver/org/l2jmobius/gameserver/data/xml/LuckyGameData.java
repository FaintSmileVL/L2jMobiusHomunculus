package org.l2jmobius.gameserver.data.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Document;

import org.l2jmobius.gameserver.data.IXmlReader;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.holders.ItemChanceHolder;
import org.l2jmobius.gameserver.model.holders.ItemPointHolder;
import org.l2jmobius.gameserver.model.holders.LuckyGameDataHolder;

/**
 * @author Sdw
 */
public class LuckyGameData implements IXmlReader
{
	private final Map<Integer, LuckyGameDataHolder> _luckyGame = new HashMap<>();
	private final AtomicInteger _serverPlay = new AtomicInteger();
	
	protected LuckyGameData()
	{
		load();
	}
	
	@Override
	public void load()
	{
		_luckyGame.clear();
		parseDatapackFile("data/LuckyGameData.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _luckyGame.size() + " lucky game data.");
	}
	
	@Override
	public void parseDocument(Document doc, File f)
	{
		forEach(doc, "list", listNode -> forEach(listNode, "luckygame", rewardNode ->
		{
			final LuckyGameDataHolder holder = new LuckyGameDataHolder(new StatSet(parseAttributes(rewardNode)));
			
			forEach(rewardNode, "common_reward", commonRewardNode -> forEach(commonRewardNode, "item", itemNode ->
			{
				final StatSet stats = new StatSet(parseAttributes(itemNode));
				holder.addCommonReward(new ItemChanceHolder(stats.getInt("id"), stats.getDouble("chance"), stats.getLong("count")));
			}));
			
			forEach(rewardNode, "unique_reward", uniqueRewardNode -> forEach(uniqueRewardNode, "item", itemNode ->
			{
				holder.addUniqueReward(new ItemPointHolder(new StatSet(parseAttributes(itemNode))));
			}));
			
			forEach(rewardNode, "modify_reward", uniqueRewardNode ->
			{
				holder.setMinModifyRewardGame(parseInteger(uniqueRewardNode.getAttributes(), "min_game"));
				holder.setMaxModifyRewardGame(parseInteger(uniqueRewardNode.getAttributes(), "max_game"));
				forEach(uniqueRewardNode, "item", itemNode ->
				{
					final StatSet stats = new StatSet(parseAttributes(itemNode));
					holder.addModifyReward(new ItemChanceHolder(stats.getInt("id"), stats.getDouble("chance"), stats.getLong("count")));
				});
			});
			
			_luckyGame.put(parseInteger(rewardNode.getAttributes(), "index"), holder);
		}));
	}
	
	public int getLuckyGameCount()
	{
		return _luckyGame.size();
	}
	
	public LuckyGameDataHolder getLuckyGameDataByIndex(int index)
	{
		return _luckyGame.get(index);
	}
	
	public int increaseGame()
	{
		return _serverPlay.incrementAndGet();
	}
	
	public int getServerPlay()
	{
		return _serverPlay.get();
	}
	
	public static LuckyGameData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final LuckyGameData INSTANCE = new LuckyGameData();
	}
}
