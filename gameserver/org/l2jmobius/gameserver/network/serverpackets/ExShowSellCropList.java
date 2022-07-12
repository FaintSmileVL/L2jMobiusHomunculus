package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.CropProcure;
import org.l2jmobius.gameserver.model.Seed;
import org.l2jmobius.gameserver.model.itemcontainer.PlayerInventory;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExShowSellCropList implements IClientOutgoingPacket
{
	private final int _manorId;
	private final Map<Integer, ItemInstance> _cropsItems = new HashMap<>();
	private final Map<Integer, CropProcure> _castleCrops = new HashMap<>();
	
	public ExShowSellCropList(PlayerInventory inventory, int manorId)
	{
		_manorId = manorId;
		for (int cropId : CastleManorManager.getInstance().getCropIds())
		{
			final ItemInstance item = inventory.getItemByItemId(cropId);
			if (item != null)
			{
				_cropsItems.put(cropId, item);
			}
		}
		
		for (CropProcure crop : CastleManorManager.getInstance().getCropProcure(_manorId, false))
		{
			if (_cropsItems.containsKey(crop.getId()) && (crop.getAmount() > 0))
			{
				_castleCrops.put(crop.getId(), crop);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_SELL_CROP_LIST.writeId(packet);
		
		packet.writeD(_manorId); // manor id
		packet.writeD(_cropsItems.size()); // size
		for (ItemInstance item : _cropsItems.values())
		{
			final Seed seed = CastleManorManager.getInstance().getSeedByCrop(item.getId());
			packet.writeD(item.getObjectId()); // Object id
			packet.writeD(item.getId()); // crop id
			packet.writeD(seed.getLevel()); // seed level
			packet.writeC(0x01);
			packet.writeD(seed.getReward(1)); // reward 1 id
			packet.writeC(0x01);
			packet.writeD(seed.getReward(2)); // reward 2 id
			if (_castleCrops.containsKey(item.getId()))
			{
				final CropProcure crop = _castleCrops.get(item.getId());
				packet.writeD(_manorId); // manor
				packet.writeQ(crop.getAmount()); // buy residual
				packet.writeQ(crop.getPrice()); // buy price
				packet.writeC(crop.getReward()); // reward
			}
			else
			{
				packet.writeD(0xFFFFFFFF); // manor
				packet.writeQ(0x00); // buy residual
				packet.writeQ(0x00); // buy price
				packet.writeC(0x00); // reward
			}
			packet.writeQ(item.getCount()); // my crops
		}
		return true;
	}
}