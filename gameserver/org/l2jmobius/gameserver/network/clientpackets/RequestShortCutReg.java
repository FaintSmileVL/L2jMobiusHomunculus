package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.ShortcutType;
import org.l2jmobius.gameserver.model.Shortcut;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ShortCutRegister;

public class RequestShortCutReg implements IClientIncomingPacket
{
	private ShortcutType _type;
	private int _id;
	private int _slot;
	private int _page;
	private int _level;
	private int _subLevel;
	private int _characterType; // 1 - player, 2 - pet
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		final int typeId = packet.readD();
		_type = ShortcutType.values()[(typeId < 1) || (typeId > 6) ? 0 : typeId];
		final int slot = packet.readD();
		_slot = slot % 12;
		_page = slot / 12;
		packet.readC(); // 228
		_id = packet.readD();
		_level = packet.readH();
		_subLevel = packet.readH(); // Sublevel
		_characterType = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if ((client.getPlayer() == null) || (_page > 23) || (_page < 0))
		{
			return;
		}
		
		final Shortcut sc = new Shortcut(_slot, _page, _type, _id, _level, _subLevel, _characterType);
		client.getPlayer().registerShortCut(sc);
		client.sendPacket(new ShortCutRegister(sc));
	}
}
