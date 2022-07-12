package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-, Sdw
 */
public class ExPledgeEmblem implements IClientOutgoingPacket
{
	private final int _crestId;
	private final int _clanId;
	private final byte[] _data;
	private final int _chunkId;
	private static final int TOTAL_SIZE = 65664;
	
	public ExPledgeEmblem(int crestId, byte[] chunkedData, int clanId, int chunkId)
	{
		_crestId = crestId;
		_data = chunkedData;
		_clanId = clanId;
		_chunkId = chunkId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_PLEDGE_EMBLEM.writeId(packet);
		
		packet.writeD(Config.SERVER_ID);
		packet.writeD(_clanId);
		packet.writeD(_crestId);
		packet.writeD(_chunkId);
		packet.writeD(TOTAL_SIZE);
		if (_data != null)
		{
			packet.writeD(_data.length);
			packet.writeB(_data);
		}
		else
		{
			packet.writeD(0);
		}
		return true;
	}
}