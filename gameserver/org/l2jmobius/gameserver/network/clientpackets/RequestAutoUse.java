package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author NviX
 */
public class RequestAutoUse implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int unk1 = packet.readC(); // C - true. This is summary amount of next data received.
		LOGGER.info("received packet RequestAutoUse with unk1:" + unk1);
		final int unk2 = packet.readC();
		LOGGER.info("and unk2: " + unk2);
		final int unk3 = packet.readC(); // Can target mobs, that attacked by other players?
		LOGGER.info("and unk3: " + unk3);
		final int unk4 = packet.readC(); // Auto pickup?
		LOGGER.info("and unk4: " + unk4);
		final int unk5 = packet.readC();
		LOGGER.info("and unk5: " + unk5);
		final int unk6 = packet.readC();
		LOGGER.info("and unk6: " + unk6);
		final int unk7 = packet.readC(); // short range :1; long: 0
		LOGGER.info("and unk7: " + unk7);
		final int unk8 = packet.readC(); // received 51 when logged in game...
		LOGGER.info("and unk8: " + unk8);
		final int unk9 = packet.readC();
		LOGGER.info("and unk9: " + unk9);
		final int unk10 = packet.readC();
		LOGGER.info("and unk10: " + unk10);
		final int unk11 = packet.readC();
		LOGGER.info("and unk11: " + unk11);
		final int unk12 = packet.readC(); // enable/ disable?
		LOGGER.info("and unk12: " + unk12);
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
	}
}