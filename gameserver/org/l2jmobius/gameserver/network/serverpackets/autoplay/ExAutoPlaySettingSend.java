package org.l2jmobius.gameserver.network.serverpackets.autoplay;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author JoeAlisson
 */
public class ExAutoPlaySettingSend implements IClientOutgoingPacket
{
	private final int _options;
	private final boolean _active;
	private final boolean _pickUp;
	private final int _nextTargetMode;
	private final boolean _longRange;
	private final int _potionPercent;
	private final boolean _respectfulHunting;
	
	public ExAutoPlaySettingSend(int options, boolean active, boolean pickUp, int nextTargetMode, boolean longRange, int potionPercent, boolean respectfulHunting)
	{
		_options = options;
		_active = active;
		_pickUp = pickUp;
		_nextTargetMode = nextTargetMode;
		_longRange = longRange;
		_potionPercent = potionPercent;
		_respectfulHunting = respectfulHunting;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_AUTOPLAY_SETTING.writeId(packet);
		packet.writeH(_options);
		packet.writeC(_active ? 1 : 0);
		packet.writeC(_pickUp ? 1 : 0);
		packet.writeH(_nextTargetMode);
		packet.writeC(_longRange ? 1 : 0);
		packet.writeD(_potionPercent);
		packet.writeD(0); // 272
		packet.writeC(_respectfulHunting ? 1 : 0);
		return true;
	}
}
