package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class Ex2ndPasswordVerify implements IClientOutgoingPacket
{
	// TODO: Enum
	public static final int PASSWORD_OK = 0x00;
	public static final int PASSWORD_WRONG = 0x01;
	public static final int PASSWORD_BAN = 0x02;
	
	private final int _wrongTentatives;
	private final int _mode;
	
	public Ex2ndPasswordVerify(int mode, int wrongTentatives)
	{
		_mode = mode;
		_wrongTentatives = wrongTentatives;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_2ND_PASSWORD_VERIFY.writeId(packet);
		
		packet.writeD(_mode);
		packet.writeD(_wrongTentatives);
		return true;
	}
}
