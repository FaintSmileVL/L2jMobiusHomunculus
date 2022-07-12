package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.CropProcure;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExShowProcureCropDetail implements IClientOutgoingPacket
{
	private final int _cropId;
	private final Map<Integer, CropProcure> _castleCrops = new HashMap<>();
	
	public ExShowProcureCropDetail(int cropId)
	{
		_cropId = cropId;
		for (Castle c : CastleManager.getInstance().getCastles())
		{
			final CropProcure cropItem = CastleManorManager.getInstance().getCropProcure(c.getResidenceId(), cropId, false);
			if ((cropItem != null) && (cropItem.getAmount() > 0))
			{
				_castleCrops.put(c.getResidenceId(), cropItem);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_PROCURE_CROP_DETAIL.writeId(packet);
		
		packet.writeD(_cropId); // crop id
		packet.writeD(_castleCrops.size()); // size
		
		for (Map.Entry<Integer, CropProcure> entry : _castleCrops.entrySet())
		{
			final CropProcure crop = entry.getValue();
			packet.writeD(entry.getKey()); // manor name
			packet.writeQ(crop.getAmount()); // buy residual
			packet.writeQ(crop.getPrice()); // buy price
			packet.writeC(crop.getReward()); // reward type
		}
		return true;
	}
}
