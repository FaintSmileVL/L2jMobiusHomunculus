package org.l2jmobius.gameserver.network.serverpackets.adenadistribution;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExDivideAdenaDone implements IClientOutgoingPacket
{
	private final boolean _isPartyLeader;
	private final boolean _isCCLeader;
	private final long _adenaCount;
	private final long _distributedAdenaCount;
	private final int _memberCount;
	private final String _distributorName;
	
	public ExDivideAdenaDone(boolean isPartyLeader, boolean isCCLeader, long adenaCount, long distributedAdenaCount, int memberCount, String distributorName)
	{
		_isPartyLeader = isPartyLeader;
		_isCCLeader = isCCLeader;
		_adenaCount = adenaCount;
		_distributedAdenaCount = distributedAdenaCount;
		_memberCount = memberCount;
		_distributorName = distributorName;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_DIVIDE_ADENA_DONE.writeId(packet);
		
		packet.writeC(_isPartyLeader ? 0x01 : 0x00);
		packet.writeC(_isCCLeader ? 0x01 : 0x00);
		packet.writeD(_memberCount);
		packet.writeQ(_distributedAdenaCount);
		packet.writeQ(_adenaCount);
		packet.writeS(_distributorName);
		return true;
	}
}
