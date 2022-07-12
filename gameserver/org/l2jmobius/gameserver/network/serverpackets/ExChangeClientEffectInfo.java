package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid
 */
public class ExChangeClientEffectInfo implements IClientOutgoingPacket
{
	public static final ExChangeClientEffectInfo STATIC_FREYA_DEFAULT = new ExChangeClientEffectInfo(0, 0, 1);
	public static final ExChangeClientEffectInfo STATIC_FREYA_DESTROYED = new ExChangeClientEffectInfo(0, 0, 2);
	
	private final int _type;
	private final int _key;
	private final int _value;
	
	/**
	 * @param type
	 *            <ul>
	 *            <li>0 - ChangeZoneState</li>
	 *            <li>1 - SetL2Fog</li>
	 *            <li>2 - postEffectData</li>
	 *            </ul>
	 * @param key
	 * @param value
	 */
	public ExChangeClientEffectInfo(int type, int key, int value)
	{
		_type = type;
		_key = key;
		_value = value;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_CLIENT_EFFECT_INFO.writeId(packet);
		
		packet.writeD(_type);
		packet.writeD(_key);
		packet.writeD(_value);
		return true;
	}
}
