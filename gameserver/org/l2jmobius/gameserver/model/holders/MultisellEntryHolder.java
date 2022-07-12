package org.l2jmobius.gameserver.model.holders;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.l2jmobius.gameserver.data.ItemTable;
import org.l2jmobius.gameserver.model.items.Item;

/**
 * @author Nik
 */
public class MultisellEntryHolder
{
	private final boolean _stackable;
	private final List<ItemChanceHolder> _ingredients;
	private final List<ItemChanceHolder> _products;
	
	public MultisellEntryHolder(List<ItemChanceHolder> ingredients, List<ItemChanceHolder> products)
	{
		_ingredients = Collections.unmodifiableList(ingredients);
		_products = Collections.unmodifiableList(products);
		_stackable = products.stream().map(i -> ItemTable.getInstance().getTemplate(i.getId())).filter(Objects::nonNull).allMatch(Item::isStackable);
	}
	
	public List<ItemChanceHolder> getIngredients()
	{
		return _ingredients;
	}
	
	public List<ItemChanceHolder> getProducts()
	{
		return _products;
	}
	
	public boolean isStackable()
	{
		return _stackable;
	}
}