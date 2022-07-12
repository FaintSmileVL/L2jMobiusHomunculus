package org.l2jmobius.gameserver.network.clientpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.MacroType;
import org.l2jmobius.gameserver.model.Macro;
import org.l2jmobius.gameserver.model.MacroCmd;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

public class RequestMakeMacro implements IClientIncomingPacket
{
	private Macro _macro;
	private int _commandsLength = 0;
	
	private static final int MAX_MACRO_LENGTH = 12;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int _id = packet.readD();
		final String _name = packet.readS();
		final String _desc = packet.readS();
		final String _acronym = packet.readS();
		final int icon = packet.readD();
		int count = packet.readC();
		if (count > MAX_MACRO_LENGTH)
		{
			count = MAX_MACRO_LENGTH;
		}
		
		final List<MacroCmd> commands = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			final int entry = packet.readC();
			final int type = packet.readC(); // 1 = skill, 3 = action, 4 = shortcut
			final int d1 = packet.readD(); // skill or page number for shortcuts
			final int d2 = packet.readC();
			final String command = packet.readS();
			_commandsLength += command.length();
			commands.add(new MacroCmd(entry, MacroType.values()[(type < 1) || (type > 6) ? 0 : type], d1, d2, command));
		}
		_macro = new Macro(_id, icon, _name, _desc, _acronym, commands);
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		if (_commandsLength > 255)
		{
			// Invalid macro. Refer to the Help file for instructions.
			player.sendPacket(SystemMessageId.INVALID_MACRO_REFER_TO_THE_HELP_FILE_FOR_INSTRUCTIONS);
			return;
		}
		if (player.getMacros().getAllMacroses().size() > 48)
		{
			// You may create up to 48 macros.
			player.sendPacket(SystemMessageId.YOU_MAY_CREATE_UP_TO_48_MACROS);
			return;
		}
		if (_macro.getName().isEmpty())
		{
			// Enter the name of the macro.
			player.sendPacket(SystemMessageId.ENTER_THE_NAME_OF_THE_MACRO);
			return;
		}
		if (_macro.getDescr().length() > 32)
		{
			// Macro descriptions may contain up to 32 characters.
			player.sendPacket(SystemMessageId.MACRO_DESCRIPTIONS_MAY_CONTAIN_UP_TO_32_CHARACTERS);
			return;
		}
		player.registerMacro(_macro);
	}
}
