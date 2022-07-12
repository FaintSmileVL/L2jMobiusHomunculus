package org.l2jmobius.gameserver.network.clientpackets;

import network.IIncomingPacket;
import network.PacketReader;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Sdw
 */
public class ExBookmarkPacket implements IClientIncomingPacket
{
	private IIncomingPacket<GameClient> _exBookmarkPacket;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int subId = packet.readD();
		
		switch (subId)
		{
			case 0:
			{
				_exBookmarkPacket = new RequestBookMarkSlotInfo();
				break;
			}
			case 1:
			{
				_exBookmarkPacket = new RequestSaveBookMarkSlot();
				break;
			}
			case 2:
			{
				_exBookmarkPacket = new RequestModifyBookMarkSlot();
				break;
			}
			case 3:
			{
				_exBookmarkPacket = new RequestDeleteBookMarkSlot();
				break;
			}
			case 4:
			{
				_exBookmarkPacket = new RequestTeleportBookMark();
				break;
			}
			case 5:
			{
				_exBookmarkPacket = new RequestChangeBookMarkSlot();
				break;
			}
		}
		return (_exBookmarkPacket != null) && _exBookmarkPacket.read(client, packet);
	}
	
	@Override
	public void run(GameClient client) throws Exception
	{
		_exBookmarkPacket.run(client);
	}
}
