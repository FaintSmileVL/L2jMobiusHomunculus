package org.l2jmobius.gameserver.model.holders;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mobius
 */
public class AutoUseSettingsHolder
{
	private final Collection<Integer> _autoSupplyItems = ConcurrentHashMap.newKeySet();
	private final Collection<Integer> _autoPotionItems = ConcurrentHashMap.newKeySet();
	private final Collection<Integer> _autoSkills = ConcurrentHashMap.newKeySet();
	private final Collection<Integer> _autoActions = ConcurrentHashMap.newKeySet();
	
	public AutoUseSettingsHolder()
	{
	}
	
	public Collection<Integer> getAutoSupplyItems()
	{
		return _autoSupplyItems;
	}
	
	public Collection<Integer> getAutoPotionItems()
	{
		return _autoPotionItems;
	}
	
	public Collection<Integer> getAutoSkills()
	{
		return _autoSkills;
	}
	
	public Collection<Integer> getAutoActions()
	{
		return _autoActions;
	}
	
	public boolean isEmpty()
	{
		return _autoSupplyItems.isEmpty() && _autoPotionItems.isEmpty() && _autoSkills.isEmpty() && _autoActions.isEmpty();
	}
}
