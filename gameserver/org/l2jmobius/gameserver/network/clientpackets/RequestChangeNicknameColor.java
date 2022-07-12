package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author KenM, Gnacik
 */
public class RequestChangeNicknameColor implements IClientIncomingPacket
{
	private static final int[] COLORS =
	{
		0x9393FF, // Pink
		0x7C49FC, // Rose Pink
		0x97F8FC, // Lemon Yellow
		0xFA9AEE, // Lilac
		0xFF5D93, // Cobalt Violet
		0x00FCA0, // Mint Green
		0xA0A601, // Peacock Green
		0x7898AF, // Yellow Ochre
		0x486295, // Chocolate
		0x999999, // Silver
	};
	
	private int _colorNum;
	private int _itemId;
	private String _title;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_colorNum = packet.readD();
		_title = packet.readS();
		_itemId = packet.readD();
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
		
		if ((_colorNum < 0) || (_colorNum >= COLORS.length))
		{
			return;
		}
		
		final ItemInstance item = player.getInventory().getItemByItemId(_itemId);
		if ((item == null) || (item.getEtcItem() == null) || (item.getEtcItem().getHandlerName() == null) || !item.getEtcItem().getHandlerName().equalsIgnoreCase("NicknameColor"))
		{
			return;
		}
		
		if (player.destroyItem("Consume", item, 1, null, true))
		{
			player.setTitle(_title);
			player.getAppearance().setTitleColor(COLORS[_colorNum]);
			player.broadcastUserInfo();
		}
	}
}
