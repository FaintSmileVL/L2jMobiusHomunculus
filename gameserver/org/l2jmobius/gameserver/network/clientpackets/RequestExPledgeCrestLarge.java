package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.CrestTable;
import org.l2jmobius.gameserver.model.Crest;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExPledgeEmblem;

/**
 * @author -Wooden-, Sdw
 */
public class RequestExPledgeCrestLarge implements IClientIncomingPacket
{
	private int _crestId;
	private int _clanId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_crestId = packet.readD();
		_clanId = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final Crest crest = CrestTable.getInstance().getCrest(_crestId);
		final byte[] data = crest != null ? crest.getData() : null;
		if (data != null)
		{
			for (int i = 0; i <= 4; i++)
			{
				final int size = Math.max(Math.min(14336, data.length - (14336 * i)), 0);
				if (size == 0)
				{
					continue;
				}
				final byte[] chunk = new byte[size];
				System.arraycopy(data, (14336 * i), chunk, 0, size);
				client.sendPacket(new ExPledgeEmblem(_crestId, chunk, _clanId, i));
			}
		}
	}
}
