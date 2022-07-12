package org.l2jmobius.gameserver.network.serverpackets;

import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.Seed;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExShowManorDefaultInfo implements IClientOutgoingPacket
{
	private final List<Seed> _crops;
	private final boolean _hideButtons;
	
	public ExShowManorDefaultInfo(boolean hideButtons)
	{
		_crops = CastleManorManager.getInstance().getCrops();
		_hideButtons = hideButtons;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_MANOR_DEFAULT_INFO.writeId(packet);
		
		packet.writeC(_hideButtons ? 0x01 : 0x00); // Hide "Seed Purchase" and "Crop Sales" buttons
		packet.writeD(_crops.size());
		for (Seed crop : _crops)
		{
			packet.writeD(crop.getCropId()); // crop Id
			packet.writeD(crop.getLevel()); // level
			packet.writeD((int) crop.getSeedReferencePrice()); // seed price
			packet.writeD((int) crop.getCropReferencePrice()); // crop price
			packet.writeC(1); // Reward 1 type
			packet.writeD(crop.getReward(1)); // Reward 1 itemId
			packet.writeC(1); // Reward 2 type
			packet.writeD(crop.getReward(2)); // Reward 2 itemId
		}
		return true;
	}
}