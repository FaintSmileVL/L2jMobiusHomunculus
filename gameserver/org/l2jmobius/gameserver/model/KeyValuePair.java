package org.l2jmobius.gameserver.model;

/**
 * @author UnAfraid
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K, V>
{
	private final K _key;
	private final V _value;
	
	public KeyValuePair(K key, V value)
	{
		_key = key;
		_value = value;
	}
	
	public K getKey()
	{
		return _key;
	}
	
	public V getValue()
	{
		return _value;
	}
}
