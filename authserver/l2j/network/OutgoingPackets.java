package l2j.network;

import network.PacketWriter;

/**
 * @author Mobius
 */
public enum OutgoingPackets
{
	INIT(0x00),
	LOGIN_FAIL(0x01),
	ACCOUNT_KICKED(0x02),
	LOGIN_OK(0x03),
	SERVER_LIST(0x04),
	PLAY_FAIL(0x06),
	PLAY_OK(0x07),
	
	PI_AGREEMENT_CHECK(0x11),
	PI_AGREEMENT_ACK(0x12),
	GG_AUTH(0x0b),
	LOGIN_OPT_FAIL(0x0D);
	
	private final int _id1;
	private final int _id2;
	
	OutgoingPackets(int id1)
	{
		this(id1, -1);
	}
	
	OutgoingPackets(int id1, int id2)
	{
		_id1 = id1;
		_id2 = id2;
	}
	
	public int getId1()
	{
		return _id1;
	}
	
	public int getId2()
	{
		return _id2;
	}
	
	public void writeId(PacketWriter packet)
	{
		packet.writeC(_id1);
		if (_id2 > 0)
		{
			packet.writeH(_id2);
		}
	}
	
	public static OutgoingPackets getPacket(int id1, int id2)
	{
		for (OutgoingPackets packet : values())
		{
			if ((packet.getId1() == id1) && (packet.getId2() == id2))
			{
				return packet;
			}
		}
		return null;
	}
}
