package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExMagicAttackInfo implements IClientOutgoingPacket
{
	// TODO: Enum
	public static final int CRITICAL = 1;
	public static final int CRITICAL_HEAL = 2;
	public static final int OVERHIT = 3;
	public static final int EVADED = 4;
	public static final int BLOCKED = 5;
	public static final int RESISTED = 6;
	public static final int IMMUNE = 7;
	public static final int IMMUNE2 = 8;
	
	private final int _caster;
	private final int _target;
	private final int _type;
	
	public ExMagicAttackInfo(int caster, int target, int type)
	{
		_caster = caster;
		_target = target;
		_type = type;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MAGIC_ATTACK_INFO.writeId(packet);
		
		packet.writeD(_caster);
		packet.writeD(_target);
		packet.writeD(_type);
		return true;
	}
}