package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.HennaData;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Zoey76
 */
public class HennaEquipList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final List<Henna> _hennaEquipList;
	
	public HennaEquipList(PlayerInstance player)
	{
		_player = player;
		_hennaEquipList = HennaData.getInstance().getHennaList(player.getClassId());
	}
	
	public HennaEquipList(PlayerInstance player, List<Henna> list)
	{
		_player = player;
		_hennaEquipList = list;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.HENNA_EQUIP_LIST.writeId(packet);
		packet.writeQ(_player.getAdena()); // activeChar current amount of Adena
		packet.writeD(3); // available equip slot
		packet.writeD(_hennaEquipList.size());
		
		for (Henna henna : _hennaEquipList)
		{
			// Player must have at least one dye in inventory
			// to be able to see the Henna that can be applied with it.
			if ((_player.getInventory().getItemByItemId(henna.getDyeItemId())) != null)
			{
				packet.writeD(henna.getDyeId()); // dye Id
				packet.writeD(henna.getDyeItemId()); // item Id of the dye
				packet.writeQ(henna.getWearCount()); // amount of dyes required
				packet.writeQ(henna.getWearFee()); // amount of Adena required
				packet.writeD(henna.isAllowedClass(_player.getClassId()) ? 0x01 : 0x00); // meet the requirement or not
				packet.writeD(0x00); // TODO: Find me!
			}
		}
		return true;
	}
}
