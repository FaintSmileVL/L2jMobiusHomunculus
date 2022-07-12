package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.BeautyShopData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.beautyshop.BeautyData;
import org.l2jmobius.gameserver.model.beautyshop.BeautyItem;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExResponseBeautyList;
import org.l2jmobius.gameserver.network.serverpackets.ExResponseBeautyRegistReset;

/**
 * @author Sdw
 */
public class RequestRegistBeauty implements IClientIncomingPacket
{
	private int _hairId;
	private int _faceId;
	private int _colorId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_hairId = packet.readD();
		_faceId = packet.readD();
		_colorId = packet.readD();
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
		
		final BeautyData beautyData = BeautyShopData.getInstance().getBeautyData(player.getRace(), player.getAppearance().getSexType());
		int requiredAdena = 0;
		int requiredBeautyShopTicket = 0;
		if (_hairId > 0)
		{
			final BeautyItem hair = beautyData.getHairList().get(_hairId);
			if (hair == null)
			{
				player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.FAILURE));
				player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
				return;
			}
			
			if (hair.getId() != player.getVisualHair())
			{
				requiredAdena += hair.getAdena();
				requiredBeautyShopTicket += hair.getBeautyShopTicket();
			}
			
			if (_colorId > 0)
			{
				final BeautyItem color = hair.getColors().get(_colorId);
				if (color == null)
				{
					player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.FAILURE));
					player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
					return;
				}
				
				requiredAdena += color.getAdena();
				requiredBeautyShopTicket += color.getBeautyShopTicket();
			}
		}
		
		if ((_faceId > 0) && (_faceId != player.getVisualFace()))
		{
			final BeautyItem face = beautyData.getFaceList().get(_faceId);
			if (face == null)
			{
				player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.FAILURE));
				player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
				return;
			}
			
			requiredAdena += face.getAdena();
			requiredBeautyShopTicket += face.getBeautyShopTicket();
		}
		
		if ((player.getAdena() < requiredAdena) || (player.getBeautyTickets() < requiredBeautyShopTicket))
		{
			player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.FAILURE));
			player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
			return;
		}
		
		if ((requiredAdena > 0) && !player.reduceAdena(getClass().getSimpleName(), requiredAdena, null, true))
		{
			player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.FAILURE));
			player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
			return;
		}
		
		if ((requiredBeautyShopTicket > 0) && !player.reduceBeautyTickets(getClass().getSimpleName(), requiredBeautyShopTicket, null, true))
		{
			player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.FAILURE));
			player.sendPacket(new ExResponseBeautyList(player, ExResponseBeautyList.SHOW_FACESHAPE));
			return;
		}
		
		if (_hairId > 0)
		{
			player.setVisualHair(_hairId);
		}
		
		if (_colorId > 0)
		{
			player.setVisualHairColor(_colorId);
		}
		
		if (_faceId > 0)
		{
			player.setVisualFace(_faceId);
		}
		
		player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.CHANGE, ExResponseBeautyRegistReset.SUCCESS));
	}
}
