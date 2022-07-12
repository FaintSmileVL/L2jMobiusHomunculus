package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.BeautyShopData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.beautyshop.BeautyItem;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExResponseBeautyList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final int _type;
	private final Map<Integer, BeautyItem> _beautyItem;
	
	public static final int SHOW_FACESHAPE = 1;
	public static final int SHOW_HAIRSTYLE = 0;
	
	public ExResponseBeautyList(PlayerInstance player, int type)
	{
		_player = player;
		_type = type;
		if (type == SHOW_HAIRSTYLE)
		{
			_beautyItem = BeautyShopData.getInstance().getBeautyData(player.getRace(), player.getAppearance().getSexType()).getHairList();
		}
		else
		{
			_beautyItem = BeautyShopData.getInstance().getBeautyData(player.getRace(), player.getAppearance().getSexType()).getFaceList();
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_BEAUTY_LIST.writeId(packet);
		
		packet.writeQ(_player.getAdena());
		packet.writeQ(_player.getBeautyTickets());
		packet.writeD(_type);
		packet.writeD(_beautyItem.size());
		for (BeautyItem item : _beautyItem.values())
		{
			packet.writeD(item.getId());
			packet.writeD(1); // Limit
		}
		packet.writeD(0);
		return true;
	}
}
