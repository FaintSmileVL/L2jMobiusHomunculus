package org.l2jmobius.gameserver.network.clientpackets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: c dddd
 * @author KenM
 */
public class GameGuardReply implements IClientIncomingPacket
{
	private static final byte[] VALID =
	{
		(byte) 0x88,
		0x40,
		0x1c,
		(byte) 0xa7,
		(byte) 0x83,
		0x42,
		(byte) 0xe9,
		0x15,
		(byte) 0xde,
		(byte) 0xc3,
		0x68,
		(byte) 0xf6,
		0x2d,
		0x23,
		(byte) 0xf1,
		0x3f,
		(byte) 0xee,
		0x68,
		0x5b,
		(byte) 0xc5,
	};
	
	private final byte[] _reply = new byte[8];
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		packet.readB(_reply, 0, 4);
		packet.readD();
		packet.readB(_reply, 4, 4);
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		try
		{
			final MessageDigest md = MessageDigest.getInstance("SHA");
			final byte[] result = md.digest(_reply);
			if (Arrays.equals(result, VALID))
			{
				client.setGameGuardOk(true);
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			LOGGER.log(Level.WARNING, "", e);
		}
	}
}
