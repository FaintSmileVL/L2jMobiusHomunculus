package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Sdw
 */
public class ExTacticalSign implements IClientOutgoingPacket
{
	private final Creature _target;
	private final int _tokenId;
	
	public ExTacticalSign(Creature target, int tokenId)
	{
		_target = target;
		_tokenId = tokenId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TACTICAL_SIGN.writeId(packet);
		
		packet.writeD(_target.getObjectId());
		packet.writeD(_tokenId);
		return true;
	}
}