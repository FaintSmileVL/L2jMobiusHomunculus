package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Collection;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.RecipeHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class RecipeBookItemList implements IClientOutgoingPacket
{
	private final Collection<RecipeHolder> _recipes;
	private final boolean _isDwarvenCraft;
	private final int _maxMp;
	
	public RecipeBookItemList(PlayerInstance player, boolean isDwarvenCraft)
	{
		_isDwarvenCraft = isDwarvenCraft;
		_maxMp = player.getMaxMp();
		_recipes = (isDwarvenCraft ? player.getDwarvenRecipeBook() : player.getCommonRecipeBook());
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.RECIPE_BOOK_ITEM_LIST.writeId(packet);
		
		packet.writeD(_isDwarvenCraft ? 0x00 : 0x01); // 0 = Dwarven - 1 = Common
		packet.writeD(_maxMp);
		
		if (_recipes == null)
		{
			packet.writeD(0);
		}
		else
		{
			packet.writeD(_recipes.size()); // number of items in recipe book
			int i = 1;
			for (RecipeHolder recipe : _recipes)
			{
				packet.writeD(recipe.getId());
				packet.writeD(i++);
			}
		}
		return true;
	}
}
