package l2j.network.serverpackets;

import network.IOutgoingPacket;
import network.PacketWriter;
import l2j.network.OutgoingPackets;

/**
 * <pre>
 * Format: dd b dddd s
 * d: session id
 * d: protocol revision
 * b: 0x90 bytes : 0x80 bytes for the scrambled RSA public key
 *                 0x10 bytes at 0x00
 * d: unknow
 * d: unknow
 * d: unknow
 * d: unknow
 * s: blowfish key
 * </pre>
 */
public class Init implements IOutgoingPacket
{
	private final int _sessionId;
	
	private final byte[] _publicKey;
	private final byte[] _blowfishKey;
	
	public Init(byte[] publickey, byte[] blowfishkey, int sessionId)
	{
		_sessionId = sessionId;
		_publicKey = publickey;
		_blowfishKey = blowfishkey;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.INIT.writeId(packet);
		
		packet.writeD(_sessionId); // session id
		packet.writeD(0x0000c621); // protocol revision
		
		packet.writeB(_publicKey); // RSA Public Key
		
		// unk GG related?
		packet.writeD(0x29DD954E);
		packet.writeD(0x77C39CFC);
		packet.writeD(0x97ADB620);
		packet.writeD(0x07BDE0F7);
		
		packet.writeB(_blowfishKey); // BlowFish key
		packet.writeC(0x00); // null termination ;)
		
		return true;
	}
}
