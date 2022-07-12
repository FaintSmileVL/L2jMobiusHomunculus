package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.CropProcure;
import org.l2jmobius.gameserver.model.Seed;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExShowCropSetting implements IClientOutgoingPacket
{
	private final int _manorId;
	private final Set<Seed> _seeds;
	private final Map<Integer, CropProcure> _current = new HashMap<>();
	private final Map<Integer, CropProcure> _next = new HashMap<>();
	
	public ExShowCropSetting(int manorId)
	{
		final CastleManorManager manor = CastleManorManager.getInstance();
		_manorId = manorId;
		_seeds = manor.getSeedsForCastle(_manorId);
		for (Seed s : _seeds)
		{
			// Current period
			CropProcure cp = manor.getCropProcure(manorId, s.getCropId(), false);
			if (cp != null)
			{
				_current.put(s.getCropId(), cp);
			}
			// Next period
			cp = manor.getCropProcure(manorId, s.getCropId(), true);
			if (cp != null)
			{
				_next.put(s.getCropId(), cp);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_CROP_SETTING.writeId(packet);
		
		packet.writeD(_manorId); // manor id
		packet.writeD(_seeds.size()); // size
		
		for (Seed s : _seeds)
		{
			packet.writeD(s.getCropId()); // crop id
			packet.writeD(s.getLevel()); // seed level
			packet.writeC(1);
			packet.writeD(s.getReward(1)); // reward 1 id
			packet.writeC(1);
			packet.writeD(s.getReward(2)); // reward 2 id
			packet.writeD(s.getCropLimit()); // next sale limit
			packet.writeD(0); // ???
			packet.writeD(s.getCropMinPrice()); // min crop price
			packet.writeD((int) s.getCropMaxPrice()); // max crop price
			// Current period
			if (_current.containsKey(s.getCropId()))
			{
				final CropProcure cp = _current.get(s.getCropId());
				packet.writeQ(cp.getStartAmount()); // buy
				packet.writeQ(cp.getPrice()); // price
				packet.writeC(cp.getReward()); // reward
			}
			else
			{
				packet.writeQ(0);
				packet.writeQ(0);
				packet.writeC(0);
			}
			// Next period
			if (_next.containsKey(s.getCropId()))
			{
				final CropProcure cp = _next.get(s.getCropId());
				packet.writeQ(cp.getStartAmount()); // buy
				packet.writeQ(cp.getPrice()); // price
				packet.writeC(cp.getReward()); // reward
			}
			else
			{
				packet.writeQ(0);
				packet.writeQ(0);
				packet.writeC(0);
			}
		}
		_next.clear();
		_current.clear();
		return true;
	}
}