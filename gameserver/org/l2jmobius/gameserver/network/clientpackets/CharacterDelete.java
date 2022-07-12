package org.l2jmobius.gameserver.network.clientpackets;

import java.util.logging.Level;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.CharacterDeleteFailType;
import org.l2jmobius.gameserver.model.CharSelectInfoPackage;
import org.l2jmobius.gameserver.model.events.Containers;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerDelete;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.CharDeleteFail;
import org.l2jmobius.gameserver.network.serverpackets.CharDeleteSuccess;
import org.l2jmobius.gameserver.network.serverpackets.CharSelectionInfo;

/**
 * @version $Revision: 1.8.2.1.2.3 $ $Date: 2005/03/27 15:29:30 $
 */
public class CharacterDelete implements IClientIncomingPacket
{
	// cd
	private int _charSlot;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_charSlot = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		// if (!client.getFloodProtectors().getCharacterSelect().tryPerformAction("CharacterDelete"))
		// {
		// client.sendPacket(new CharDeleteFail(CharacterDeleteFailType.UNKNOWN));
		// return;
		// }
		
		try
		{
			final CharacterDeleteFailType failType = client.markToDeleteChar(_charSlot);
			switch (failType)
			{
				case NONE:// Success!
				{
					client.sendPacket(new CharDeleteSuccess());
					final CharSelectInfoPackage charInfo = client.getCharSelection(_charSlot);
					EventDispatcher.getInstance().notifyEvent(new OnPlayerDelete(charInfo.getObjectId(), charInfo.getName(), client), Containers.Players());
					break;
				}
				default:
				{
					client.sendPacket(new CharDeleteFail(failType));
					break;
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Error:", e);
		}
		
		final CharSelectionInfo cl = new CharSelectionInfo(client.getAccountName(), client.getSessionId().playOkID1, 0);
		client.sendPacket(cl);
		client.setCharSelection(cl.getCharInfo());
	}
}
