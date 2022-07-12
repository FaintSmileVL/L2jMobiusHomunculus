package org.l2jmobius.gameserver.model.options;

import java.util.List;
import java.util.Map;

import util.Rnd;

/**
 * @author Pere, Mobius
 */
public class OptionDataCategory
{
	private final Map<Options, Double> _options;
	private final List<Integer> _itemIds;
	private final double _chance;
	
	public OptionDataCategory(Map<Options, Double> options, List<Integer> itemIds, double chance)
	{
		_options = options;
		_itemIds = itemIds;
		_chance = chance;
	}
	
	Options getRandomOptions()
	{
		Options result = null;
		do
		{
			double random = Rnd.nextDouble() * 100.0;
			for (Map.Entry<Options, Double> entry : _options.entrySet())
			{
				if (entry.getValue() >= random)
				{
					result = entry.getKey();
					break;
				}
				
				random -= entry.getValue();
			}
		}
		while (result == null);
		return result;
	}
	
	public List<Integer> getItemIds()
	{
		return _itemIds;
	}
	
	public double getChance()
	{
		return _chance;
	}
}