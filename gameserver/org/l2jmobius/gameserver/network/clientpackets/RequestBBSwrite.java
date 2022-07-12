package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.handler.CommunityBoardHandler;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * RequestBBSwrite client packet implementation.
 * @author -Wooden-, Zoey76
 */
public class RequestBBSwrite implements IClientIncomingPacket
{
	private String _url;
	private String _arg1;
	private String _arg2;
	private String _arg3;
	private String _arg4;
	private String _arg5;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_url = packet.readS();
		_arg1 = packet.readS();
		_arg2 = packet.readS();
		_arg3 = packet.readS();
		_arg4 = packet.readS();
		_arg5 = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		CommunityBoardHandler.getInstance().handleWriteCommand(client.getPlayer(), _url, _arg1, _arg2, _arg3, _arg4, _arg5);
	}
}