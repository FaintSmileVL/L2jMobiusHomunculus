package org.l2jmobius.gameserver.model.instancezone.conditions;

import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.InstanceTemplate;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Instance item condition
 * @author malyelfik
 */
public class ConditionItem extends Condition
{
	private final int _itemId;
	private final long _count;
	private final boolean _take;
	
	public ConditionItem(InstanceTemplate template, StatSet parameters, boolean onlyLeader, boolean showMessageAndHtml)
	{
		super(template, parameters, onlyLeader, showMessageAndHtml);
		// Load params
		_itemId = parameters.getInt("id");
		_count = parameters.getLong("count");
		_take = parameters.getBoolean("take", false);
		// Set message
		setSystemMessage(SystemMessageId.C1_S_ITEM_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED, (msg, player) -> msg.addString(player.getName()));
	}
	
	@Override
	protected boolean test(PlayerInstance player, Npc npc)
	{
		return player.getInventory().getInventoryItemCount(_itemId, -1) >= _count;
	}
	
	@Override
	protected void onSuccess(PlayerInstance player)
	{
		if (_take)
		{
			player.destroyItemByItemId("InstanceConditionDestroy", _itemId, _count, null, true);
		}
	}
}