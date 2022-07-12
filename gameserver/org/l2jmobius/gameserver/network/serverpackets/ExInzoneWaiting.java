package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import network.PacketWriter;
import util.Chronos;
import org.l2jmobius.gameserver.instancemanager.InstanceManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExInzoneWaiting implements IClientOutgoingPacket
{
	private final int _currentTemplateId;
	private final Map<Integer, Long> _instanceTimes;
	private final boolean _hide;
	
	public ExInzoneWaiting(PlayerInstance player, boolean hide)
	{
		final Instance instance = InstanceManager.getInstance().getPlayerInstance(player, false);
		_currentTemplateId = ((instance != null) && (instance.getTemplateId() >= 0)) ? instance.getTemplateId() : -1;
		_instanceTimes = InstanceManager.getInstance().getAllInstanceTimes(player);
		_hide = hide;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_INZONE_WAITING_INFO.writeId(packet);
		
		packet.writeC(_hide ? 0x00 : 0x01); // Grand Crusade
		packet.writeD(_currentTemplateId);
		packet.writeD(_instanceTimes.size());
		for (Entry<Integer, Long> entry : _instanceTimes.entrySet())
		{
			final long instanceTime = TimeUnit.MILLISECONDS.toSeconds(entry.getValue() - Chronos.currentTimeMillis());
			packet.writeD(entry.getKey());
			packet.writeD((int) instanceTime);
		}
		return true;
	}
}
