package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.CropProcure;
import org.l2jmobius.gameserver.model.Seed;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExShowCropInfo implements IClientOutgoingPacket
{
	private final List<CropProcure> _crops;
	private final int _manorId;
	private final boolean _hideButtons;
	
	public ExShowCropInfo(int manorId, boolean nextPeriod, boolean hideButtons)
	{
		_manorId = manorId;
		_hideButtons = hideButtons;
		
		final CastleManorManager manor = CastleManorManager.getInstance();
		_crops = (nextPeriod && !manor.isManorApproved()) ? null : manor.getCropProcure(manorId, nextPeriod);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_CROP_INFO.writeId(packet);
		
		packet.writeC(_hideButtons ? 0x01 : 0x00); // Hide "Crop Sales" button
		packet.writeD(_manorId); // Manor ID
		packet.writeD(0x00);
		if (_crops != null)
		{
			packet.writeD(_crops.size());
			for (CropProcure crop : _crops)
			{
				packet.writeD(crop.getId()); // Crop id
				packet.writeQ(crop.getAmount()); // Buy residual
				packet.writeQ(crop.getStartAmount()); // Buy
				packet.writeQ(crop.getPrice()); // Buy price
				packet.writeC(crop.getReward()); // Reward
				final Seed seed = CastleManorManager.getInstance().getSeedByCrop(crop.getId());
				if (seed == null)
				{
					packet.writeD(0); // Seed level
					packet.writeC(0x01); // Reward 1
					packet.writeD(0); // Reward 1 - item id
					packet.writeC(0x01); // Reward 2
					packet.writeD(0); // Reward 2 - item id
				}
				else
				{
					packet.writeD(seed.getLevel()); // Seed level
					packet.writeC(0x01); // Reward 1
					packet.writeD(seed.getReward(1)); // Reward 1 - item id
					packet.writeC(0x01); // Reward 2
					packet.writeD(seed.getReward(2)); // Reward 2 - item id
				}
			}
		}
		return true;
	}
}