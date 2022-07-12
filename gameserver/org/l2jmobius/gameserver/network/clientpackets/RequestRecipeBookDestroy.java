package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.RecipeData;
import org.l2jmobius.gameserver.enums.PrivateStoreType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.RecipeHolder;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.RecipeBookItemList;

public class RequestRecipeBookDestroy implements IClientIncomingPacket
{
	private int _recipeID;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_recipeID = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (!client.getFloodProtectors().getTransaction().tryPerformAction("RecipeDestroy"))
		{
			return;
		}
		
		if ((player.getPrivateStoreType() == PrivateStoreType.MANUFACTURE) || player.isCrafting())
		{
			player.sendPacket(SystemMessageId.YOU_MAY_NOT_ALTER_YOUR_RECIPE_BOOK_WHILE_ENGAGED_IN_MANUFACTURING);
			return;
		}
		
		final RecipeHolder rp = RecipeData.getInstance().getRecipe(_recipeID);
		if (rp == null)
		{
			client.sendPacket(SystemMessageId.THE_RECIPE_IS_INCORRECT);
			return;
		}
		
		// Remove the recipe from the list.
		player.unregisterRecipeList(_recipeID);
		
		// Send the new recipe book.
		player.sendPacket(new RecipeBookItemList(player, rp.isDwarvenRecipe()));
	}
}