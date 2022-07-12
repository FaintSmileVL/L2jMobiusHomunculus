package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.CharNameTable;
import org.l2jmobius.gameserver.data.xml.FakePlayerData;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExIsCharNameCreatable;
import org.l2jmobius.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class RequestCharacterNameCreatable implements IClientIncomingPacket
{
	private String _name;
	
	public static final int CHARACTER_CREATE_FAILED = 1;
	public static final int NAME_ALREADY_EXISTS = 2;
	public static final int INVALID_LENGTH = 3;
	public static final int INVALID_NAME = 4;
	public static final int CANNOT_CREATE_SERVER = 5;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_name = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final int charId = CharNameTable.getInstance().getIdByName(_name);
		int result;
		if (!Util.isAlphaNumeric(_name) || !isValidName(_name))
		{
			result = INVALID_NAME;
		}
		else if (charId > 0)
		{
			result = NAME_ALREADY_EXISTS;
		}
		else if (FakePlayerData.getInstance().getProperName(_name) != null)
		{
			result = NAME_ALREADY_EXISTS;
		}
		else if (_name.length() > 16)
		{
			result = INVALID_LENGTH;
		}
		else
		{
			result = -1;
		}
		
		client.sendPacket(new ExIsCharNameCreatable(result));
	}
	
	private boolean isValidName(String text)
	{
		return Config.CHARNAME_TEMPLATE_PATTERN.matcher(text).matches();
	}
}