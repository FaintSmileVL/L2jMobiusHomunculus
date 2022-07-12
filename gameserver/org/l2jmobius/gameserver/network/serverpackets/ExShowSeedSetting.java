package org.l2jmobius.gameserver.network.serverpackets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManorManager;
import org.l2jmobius.gameserver.model.Seed;
import org.l2jmobius.gameserver.model.SeedProduction;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author l3x
 */
public class ExShowSeedSetting implements IClientOutgoingPacket
{
	private final int _manorId;
	private final Set<Seed> _seeds;
	private final Map<Integer, SeedProduction> _current = new HashMap<>();
	private final Map<Integer, SeedProduction> _next = new HashMap<>();
	
	public ExShowSeedSetting(int manorId)
	{
		final CastleManorManager manor = CastleManorManager.getInstance();
		_manorId = manorId;
		_seeds = manor.getSeedsForCastle(_manorId);
		for (Seed s : _seeds)
		{
			// Current period
			SeedProduction sp = manor.getSeedProduct(manorId, s.getSeedId(), false);
			if (sp != null)
			{
				_current.put(s.getSeedId(), sp);
			}
			// Next period
			sp = manor.getSeedProduct(manorId, s.getSeedId(), true);
			if (sp != null)
			{
				_next.put(s.getSeedId(), sp);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_SHOW_SEED_SETTING.writeId(packet);
		
		packet.writeD(_manorId); // manor id
		packet.writeD(_seeds.size()); // size
		
		for (Seed s : _seeds)
		{
			packet.writeD(s.getSeedId()); // seed id
			packet.writeD(s.getLevel()); // level
			packet.writeC(1);
			packet.writeD(s.getReward(1)); // reward 1 id
			packet.writeC(1);
			packet.writeD(s.getReward(2)); // reward 2 id
			packet.writeD(s.getSeedLimit()); // next sale limit
			packet.writeD((int) s.getSeedReferencePrice()); // price for castle to produce 1
			packet.writeD((int) s.getSeedMinPrice()); // min seed price
			packet.writeD((int) s.getSeedMaxPrice()); // max seed price
			// Current period
			if (_current.containsKey(s.getSeedId()))
			{
				final SeedProduction sp = _current.get(s.getSeedId());
				packet.writeQ(sp.getStartAmount()); // sales
				packet.writeQ(sp.getPrice()); // price
			}
			else
			{
				packet.writeQ(0);
				packet.writeQ(0);
			}
			// Next period
			if (_next.containsKey(s.getSeedId()))
			{
				final SeedProduction sp = _next.get(s.getSeedId());
				packet.writeQ(sp.getStartAmount()); // sales
				packet.writeQ(sp.getPrice()); // price
			}
			else
			{
				packet.writeQ(0);
				packet.writeQ(0);
			}
		}
		_current.clear();
		_next.clear();
		return true;
	}
}