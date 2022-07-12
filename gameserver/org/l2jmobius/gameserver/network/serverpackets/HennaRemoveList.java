package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Henna;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Zoey76
 */
public class HennaRemoveList implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	
	public HennaRemoveList(PlayerInstance player)
	{
		_player = player;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.HENNA_UNEQUIP_LIST.writeId(packet);
		packet.writeQ(_player.getAdena());
		final boolean premiumSlotEnabled = _player.getHenna(4) != null;
		packet.writeD(premiumSlotEnabled ? 0x04 : 0x03); // seems to be max size
		packet.writeD((premiumSlotEnabled ? 4 : 3) - _player.getHennaEmptySlots()); // slots used
		for (Henna henna : _player.getHennaList())
		{
			if (henna != null)
			{
				packet.writeD(henna.getDyeId());
				packet.writeD(henna.getDyeItemId());
				packet.writeQ(henna.getCancelCount());
				packet.writeQ(henna.getCancelFee());
				packet.writeD(0x00);
				packet.writeD(0x00);
			}
		}
		return true;
	}
}
