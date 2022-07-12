package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.ConnectionState;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Request Save Key Mapping client packet.
 * @author Mobius
 */
public class RequestSaveKeyMapping implements IClientIncomingPacket
{
	public static final String UI_KEY_MAPPING_VAR = "UI_KEY_MAPPING";
	public static final String SPLIT_VAR = "	";
	private byte[] _uiKeyMapping;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int dataSize = packet.readD();
		if (dataSize > 0)
		{
			_uiKeyMapping = packet.readB(dataSize);
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (!Config.STORE_UI_SETTINGS || //
			(player == null) || //
			(_uiKeyMapping == null) || //
			(client.getConnectionState() != ConnectionState.IN_GAME))
		{
			return;
		}
		
		String uiKeyMapping = "";
		for (Byte b : _uiKeyMapping)
		{
			uiKeyMapping += b + SPLIT_VAR;
		}
		player.getVariables().set(UI_KEY_MAPPING_VAR, uiKeyMapping);
	}
}
