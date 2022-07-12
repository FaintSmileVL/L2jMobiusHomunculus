package org.l2jmobius.gameserver.model.buylist;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author NosBit
 */
public class ProductList
{
	private final int _listId;
	private final Map<Integer, Product> _products = new LinkedHashMap<>();
	private Set<Integer> _allowedNpcs = null;
	
	public ProductList(int listId)
	{
		_listId = listId;
	}
	
	public int getListId()
	{
		return _listId;
	}
	
	public Collection<Product> getProducts()
	{
		return _products.values();
	}
	
	public Product getProductByItemId(int itemId)
	{
		return _products.get(itemId);
	}
	
	public void addProduct(Product product)
	{
		_products.put(product.getItemId(), product);
	}
	
	public void addAllowedNpc(int npcId)
	{
		if (_allowedNpcs == null)
		{
			_allowedNpcs = new HashSet<>();
		}
		_allowedNpcs.add(npcId);
	}
	
	public boolean isNpcAllowed(int npcId)
	{
		return (_allowedNpcs != null) && _allowedNpcs.contains(npcId);
	}
	
	// public Set<Integer> getNpcsAllowed()
	// {
	// return _allowedNpcs;
	// }
}
