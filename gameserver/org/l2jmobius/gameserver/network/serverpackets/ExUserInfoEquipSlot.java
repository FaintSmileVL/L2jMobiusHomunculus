package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.InventorySlot;
import org.l2jmobius.gameserver.model.VariationInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.itemcontainer.PlayerInventory;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExUserInfoEquipSlot extends AbstractMaskPacket<InventorySlot>
{
	private final PlayerInstance _player;
	
	private final byte[] _masks = new byte[]
	{
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00,
		(byte) 0x00, // 152
		(byte) 0x00, // 152
		(byte) 0x00, // 152
	};
	
	public ExUserInfoEquipSlot(PlayerInstance player)
	{
		this(player, true);
	}
	
	public ExUserInfoEquipSlot(PlayerInstance player, boolean addAll)
	{
		_player = player;
		if (addAll)
		{
			addComponentType(InventorySlot.values());
		}
	}
	
	@Override
	protected byte[] getMasks()
	{
		return _masks;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_USER_INFO_EQUIP_SLOT.writeId(packet);
		
		packet.writeD(_player.getObjectId());
		packet.writeH(InventorySlot.values().length); // 152
		packet.writeB(_masks);
		
		final PlayerInventory inventory = _player.getInventory();
		for (InventorySlot slot : InventorySlot.values())
		{
			if (containsMask(slot))
			{
				final VariationInstance augment = inventory.getPaperdollAugmentation(slot.getSlot());
				packet.writeH(22); // 10 + 4 * 3
				packet.writeD(inventory.getPaperdollObjectId(slot.getSlot()));
				packet.writeD(inventory.getPaperdollItemId(slot.getSlot()));
				packet.writeD(augment != null ? augment.getOption1Id() : 0);
				packet.writeD(augment != null ? augment.getOption2Id() : 0);
				packet.writeD(inventory.getPaperdollItemVisualId(slot.getSlot()));
			}
		}
		return true;
	}
}