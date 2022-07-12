package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.model.stats.BaseStat;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Zoey76
 */
public class HennaItemDrawInfo implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final Henna _henna;
	
	public HennaItemDrawInfo(Henna henna, PlayerInstance player)
	{
		_henna = henna;
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.HENNA_ITEM_INFO.writeId(packet);
		
		packet.writeD(_henna.getDyeId()); // symbol Id
		packet.writeD(_henna.getDyeItemId()); // item id of dye
		packet.writeQ(_henna.getWearCount()); // total amount of dye require
		packet.writeQ(_henna.getWearFee()); // total amount of Adena require to draw symbol
		packet.writeD(_henna.isAllowedClass(_player.getClassId()) ? 0x01 : 0x00); // able to draw or not 0 is false and 1 is true
		packet.writeQ(_player.getAdena());
		packet.writeD(_player.getINT()); // current INT
		packet.writeH(_player.getINT() + _player.getHennaValue(BaseStat.INT)); // equip INT
		packet.writeD(_player.getSTR()); // current STR
		packet.writeH(_player.getSTR() + _player.getHennaValue(BaseStat.STR)); // equip STR
		packet.writeD(_player.getCON()); // current CON
		packet.writeH(_player.getCON() + _player.getHennaValue(BaseStat.CON)); // equip CON
		packet.writeD(_player.getMEN()); // current MEN
		packet.writeH(_player.getMEN() + _player.getHennaValue(BaseStat.MEN)); // equip MEN
		packet.writeD(_player.getDEX()); // current DEX
		packet.writeH(_player.getDEX() + _player.getHennaValue(BaseStat.DEX)); // equip DEX
		packet.writeD(_player.getWIT()); // current WIT
		packet.writeH(_player.getWIT() + _player.getHennaValue(BaseStat.WIT)); // equip WIT
		packet.writeD(_player.getLUC()); // current LUC
		packet.writeH(_player.getLUC() + _player.getHennaValue(BaseStat.LUC)); // equip LUC
		packet.writeD(_player.getCHA()); // current CHA
		packet.writeH(_player.getCHA() + _player.getHennaValue(BaseStat.CHA)); // equip CHA
		packet.writeD(0x00); // TODO: Find me!
		return true;
	}
}
