package org.l2jmobius.gameserver.model.options;

import java.util.ArrayList;
import java.util.List;

import util.Rnd;

/**
 * @author Pere, Mobius
 */
public class OptionDataGroup
{
	private final List<OptionDataCategory> _categories;
	
	public OptionDataGroup(List<OptionDataCategory> categories)
	{
		_categories = categories;
	}
	
	Options getRandomEffect(int itemId)
	{
		final List<OptionDataCategory> exclutions = new ArrayList<>();
		Options result = null;
		do
		{
			double random = Rnd.nextDouble() * 100.0;
			for (OptionDataCategory category : _categories)
			{
				if (!category.getItemIds().isEmpty() && !category.getItemIds().contains(itemId))
				{
					if (!exclutions.contains(category))
					{
						exclutions.add(category);
					}
					continue;
				}
				if (category.getChance() >= random)
				{
					result = category.getRandomOptions();
					break;
				}
				random -= category.getChance();
			}
		}
		while ((result == null) && (exclutions.size() < _categories.size()));
		
		return result;
	}
}