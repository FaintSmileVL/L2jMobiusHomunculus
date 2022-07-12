package org.l2jmobius.gameserver.model.events.impl.creature.player;

import org.l2jmobius.gameserver.model.VariationInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventType;
import org.l2jmobius.gameserver.model.events.impl.IBaseEvent;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class OnPlayerAugment implements IBaseEvent
{
	private final PlayerInstance _player;
	private final ItemInstance _item;
	private final VariationInstance _augmentation;
	private final boolean _isAugment; // true = is being augmented // false = augment is being removed
	
	public OnPlayerAugment(PlayerInstance player, ItemInstance item, VariationInstance augment, boolean isAugment)
	{
		_player = player;
		_item = item;
		_augmentation = augment;
		_isAugment = isAugment;
	}
	
	public PlayerInstance getPlayer()
	{
		return _player;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public VariationInstance getAugmentation()
	{
		return _augmentation;
	}
	
	public boolean isAugment()
	{
		return _isAugment;
	}
	
	@Override
	public EventType getType()
	{
		return EventType.ON_PLAYER_AUGMENT;
	}
}
