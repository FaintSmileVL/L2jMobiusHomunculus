package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.SiegeDefenderList;

/**
 * @version $Revision: 1.3.4.2 $ $Date: 2005/03/27 15:29:30 $
 */
public class RequestSiegeDefenderList implements IClientIncomingPacket
{
	private int _castleId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_castleId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final Castle castle = CastleManager.getInstance().getCastleById(_castleId);
		if (castle == null)
		{
			return;
		}
		client.sendPacket(new SiegeDefenderList(castle));
	}
}
