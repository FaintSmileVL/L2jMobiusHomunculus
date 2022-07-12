package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Bonux (bonuxq@gmail.com)
 * @date 09.09.2019
 **/
public class ExTryEnchantArtifactResult implements IClientOutgoingPacket
{
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int ERROR = 2;
	
	public static final ExTryEnchantArtifactResult ERROR_PACKET = new ExTryEnchantArtifactResult(ERROR, 0);
	
	private final int _state;
	private final int _enchant;
	
	public ExTryEnchantArtifactResult(int state, int enchant)
	{
		_state = state;
		_enchant = enchant;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TRY_ENCHANT_ARTIFACT_RESULT.writeId(packet);
		
		packet.writeD(_state);
		packet.writeD(_enchant);
		packet.writeD(0);
		packet.writeD(0);
		packet.writeD(0);
		return true;
	}
}