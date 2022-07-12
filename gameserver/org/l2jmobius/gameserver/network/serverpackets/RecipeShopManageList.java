package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.RecipeData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.RecipeHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class RecipeShopManageList implements IClientOutgoingPacket
{
	private final PlayerInstance _seller;
	private final boolean _isDwarven;
	private final Collection<RecipeHolder> _recipes;
	private List<Entry<Integer, Long>> _manufacture;
	
	public RecipeShopManageList(PlayerInstance seller, boolean isDwarven)
	{
		_seller = seller;
		_isDwarven = isDwarven;
		_recipes = (isDwarven && (_seller.getCreateItemLevel() > 0)) ? _seller.getDwarvenRecipeBook() : _seller.getCommonRecipeBook();
		if (_seller.hasManufactureShop())
		{
			_manufacture = new ArrayList<>();
			for (Entry<Integer, Long> item : _seller.getManufactureItems().entrySet())
			{
				final RecipeHolder recipe = RecipeData.getInstance().getRecipe(item.getKey());
				if (((recipe != null) && (recipe.isDwarvenRecipe() == _isDwarven)) && seller.hasRecipeList(recipe.getId()))
				{
					_manufacture.add(item);
				}
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.RECIPE_SHOP_MANAGE_LIST.writeId(packet);
		
		packet.writeD(_seller.getObjectId());
		packet.writeD((int) _seller.getAdena());
		packet.writeD(_isDwarven ? 0x00 : 0x01);
		
		if ((_recipes == null) || _recipes.isEmpty())
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
		
		if ((_manufacture == null) || _manufacture.isEmpty())
		{
			packet.writeD(0x00);
		}
		else
		{
			packet.writeD(_manufacture.size());
			for (Entry<Integer, Long> item : _manufacture)
			{
				packet.writeD(item.getKey());
				packet.writeD(0x00); // CanCraft?
				packet.writeQ(item.getValue());
			}
		}
		return true;
	}
}
