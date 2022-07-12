package org.l2jmobius.gameserver.model.alchemy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.holders.ItemHolder;

/**
 * @author Sdw
 */
public class AlchemyCraftData
{
	private final int _id;
	private final int _level;
	private final int _grade;
	private final int _category;
	private Set<ItemHolder> _ingredients;
	private ItemHolder _productionSuccess;
	private ItemHolder _productionFailure;
	
	public AlchemyCraftData(StatSet set)
	{
		_id = set.getInt("id");
		_level = set.getInt("level");
		_grade = set.getInt("grade");
		_category = set.getInt("category");
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getLevel()
	{
		return _level;
	}
	
	public int getGrade()
	{
		return _grade;
	}
	
	public int getCategory()
	{
		return _category;
	}
	
	public void addIngredient(ItemHolder ingredient)
	{
		if (_ingredients == null)
		{
			_ingredients = new HashSet<>();
		}
		_ingredients.add(ingredient);
	}
	
	public Set<ItemHolder> getIngredients()
	{
		return _ingredients != null ? _ingredients : Collections.emptySet();
	}
	
	public void setProductionSuccess(ItemHolder item)
	{
		_productionSuccess = item;
	}
	
	public ItemHolder getProductionSuccess()
	{
		return _productionSuccess;
	}
	
	public void setProductionFailure(ItemHolder item)
	{
		_productionFailure = item;
	}
	
	public ItemHolder getProductionFailure()
	{
		return _productionFailure;
	}
}
