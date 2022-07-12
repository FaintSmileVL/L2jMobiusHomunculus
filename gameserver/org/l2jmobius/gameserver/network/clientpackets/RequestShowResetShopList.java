package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.BeautyShopData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.beautyshop.BeautyData;
import org.l2jmobius.gameserver.model.beautyshop.BeautyItem;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExResponseBeautyRegistReset;

/**
 * @author Sdw
 */
public class RequestShowResetShopList implements IClientIncomingPacket
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
		if (_hairId > 0)
		{
			final BeautyItem hair = beautyData.getHairList().get(_hairId);
			if (hair == null)
			{
				player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.RESTORE, ExResponseBeautyRegistReset.FAILURE));
				return;
			}
			
			requiredAdena += hair.getResetAdena();
			if (_colorId > 0)
			{
				final BeautyItem color = hair.getColors().get(_colorId);
				if (color == null)
				{
					player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.RESTORE, ExResponseBeautyRegistReset.FAILURE));
					return;
				}
				
				requiredAdena += color.getResetAdena();
			}
		}
		
		if (_faceId > 0)
		{
			final BeautyItem face = beautyData.getFaceList().get(_faceId);
			if (face == null)
			{
				player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.RESTORE, ExResponseBeautyRegistReset.FAILURE));
				return;
			}
			
			requiredAdena += face.getResetAdena();
		}
		
		if ((player.getAdena() < requiredAdena))
		{
			player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.RESTORE, ExResponseBeautyRegistReset.FAILURE));
			return;
		}
		
		if ((requiredAdena > 0) && !player.reduceAdena(getClass().getSimpleName(), requiredAdena, null, true))
		{
			player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.RESTORE, ExResponseBeautyRegistReset.FAILURE));
			return;
		}
		
		player.getVariables().remove("visualHairId");
		player.getVariables().remove("visualHairColorId");
		player.getVariables().remove("visualFaceId");
		
		player.sendPacket(new ExResponseBeautyRegistReset(player, ExResponseBeautyRegistReset.RESTORE, ExResponseBeautyRegistReset.SUCCESS));
	}
}