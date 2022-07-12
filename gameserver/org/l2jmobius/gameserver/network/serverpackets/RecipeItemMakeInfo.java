package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.RecipeData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.RecipeHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class RecipeItemMakeInfo implements IClientOutgoingPacket
{
	private final int _id;
	private final PlayerInstance _player;
	private final Boolean _success;
	private final long _offeringMaximumAdena;
	
	public RecipeItemMakeInfo(int id, PlayerInstance player, boolean success, long offeringMaximumAdena)
	{
		_id = id;
		_player = player;
		_success = success;
		_offeringMaximumAdena = offeringMaximumAdena;
	}
	
	public RecipeItemMakeInfo(int id, PlayerInstance player, boolean success)
	{
		_id = id;
		_player = player;
		_success = success;
		_offeringMaximumAdena = 0;
	}
	
	public RecipeItemMakeInfo(int id, PlayerInstance player, long offeringMaximumAdena)
	{
		_id = id;
		_player = player;
		_success = null;
		_offeringMaximumAdena = offeringMaximumAdena;
	}
	
	public RecipeItemMakeInfo(int id, PlayerInstance player)
	{
		_id = id;
		_player = player;
		_success = null;
		_offeringMaximumAdena = 0;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		final RecipeHolder recipe = RecipeData.getInstance().getRecipe(_id);
		if (recipe != null)
		{
			OutgoingPackets.RECIPE_ITEM_MAKE_INFO.writeId(packet);
			packet.writeD(_id);
			packet.writeD(recipe.isDwarvenRecipe() ? 0 : 1); // 0 = Dwarven - 1 = Common
			packet.writeD((int) _player.getCurrentMp());
			packet.writeD(_player.getMaxMp());
			packet.writeD(_success == null ? -1 : (_success ? 1 : 0)); // item creation none/success/failed
			packet.writeC(_offeringMaximumAdena > 0 ? 1 : 0); // Show offering window.
			packet.writeQ(_offeringMaximumAdena); // Adena worth of items for maximum offering.
			return true;
		}
		LOGGER.info("Character: " + _player + ": Requested unexisting recipe with id = " + _id);
		return false;
	}
}
