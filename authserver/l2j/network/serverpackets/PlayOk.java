package l2j.network.serverpackets;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.SessionKey;
import l2j.network.OutgoingPackets;

public class PlayOk implements IOutgoingPacket
{
	private final int _playOk1;
	private final int _playOk2;
	
	public PlayOk(SessionKey sessionKey)
	{
		_playOk1 = sessionKey.playOkID1;
		_playOk2 = sessionKey.playOkID2;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PLAY_OK.writeId(packet);
		packet.writeD(_playOk1);
		packet.writeD(_playOk2);
		return true;
	}
}
