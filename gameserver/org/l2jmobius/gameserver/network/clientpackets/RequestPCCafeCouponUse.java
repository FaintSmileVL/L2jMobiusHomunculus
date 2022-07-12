package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: (ch) S
 * @author -Wooden- TODO: GodKratos: This packet is wrong in Gracia Final!!
 */
public class RequestPCCafeCouponUse implements IClientIncomingPacket
{
	private String _str;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_str = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		LOGGER.info("C5: RequestPCCafeCouponUse: S: " + _str);
	}
}
