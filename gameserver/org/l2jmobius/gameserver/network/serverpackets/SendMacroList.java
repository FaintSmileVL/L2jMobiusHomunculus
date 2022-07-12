package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.MacroUpdateType;
import org.l2jmobius.gameserver.model.Macro;
import org.l2jmobius.gameserver.model.MacroCmd;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class SendMacroList implements IClientOutgoingPacket
{
	private final int _count;
	private final Macro _macro;
	private final MacroUpdateType _updateType;
	
	public SendMacroList(int count, Macro macro, MacroUpdateType updateType)
	{
		_count = count;
		_macro = macro;
		_updateType = updateType;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MACRO_LIST.writeId(packet);
		
		packet.writeC(_updateType.getId());
		packet.writeD(_updateType != MacroUpdateType.LIST ? _macro.getId() : 0x00); // modified, created or deleted macro's id
		packet.writeC(_count); // count of Macros
		packet.writeC(_macro != null ? 1 : 0); // unknown
		
		if ((_macro != null) && (_updateType != MacroUpdateType.DELETE))
		{
			packet.writeD(_macro.getId()); // Macro ID
			packet.writeS(_macro.getName()); // Macro Name
			packet.writeS(_macro.getDescr()); // Desc
			packet.writeS(_macro.getAcronym()); // acronym
			packet.writeD(_macro.getIcon()); // icon
			
			packet.writeC(_macro.getCommands().size()); // count
			
			int i = 1;
			for (MacroCmd cmd : _macro.getCommands())
			{
				packet.writeC(i++); // command count
				packet.writeC(cmd.getType().ordinal()); // type 1 = skill, 3 = action, 4 = shortcut
				packet.writeD(cmd.getD1()); // skill id
				packet.writeC(cmd.getD2()); // shortcut id
				packet.writeS(cmd.getCmd()); // command name
			}
		}
		return true;
	}
}
