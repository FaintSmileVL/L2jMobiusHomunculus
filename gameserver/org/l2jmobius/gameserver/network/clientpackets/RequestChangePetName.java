package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.PetNameTable;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * @version $Revision: 1.3.4.4 $ $Date: 2005/04/06 16:13:48 $
 */
public class RequestChangePetName implements IClientIncomingPacket
{
	private String _name;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_name = packet.readS();
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
		
		final Summon pet = player.getPet();
		if (pet == null)
		{
			return;
		}
		
		if (!pet.isPet())
		{
			player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_A_PET);
			return;
		}
		
		if (pet.getName() != null)
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_SET_THE_NAME_OF_THE_PET);
			return;
		}
		
		if (PetNameTable.getInstance().doesPetNameExist(_name, pet.getTemplate().getId()))
		{
			player.sendPacket(SystemMessageId.THIS_IS_ALREADY_IN_USE_BY_ANOTHER_PET);
			return;
		}
		
		if ((_name.length() < 3) || (_name.length() > 16))
		{
			// player.sendPacket(SystemMessageId.YOUR_PET_S_NAME_CAN_BE_UP_TO_8_CHARACTERS_IN_LENGTH);
			player.sendMessage("Your pet's name can be up to 16 characters in length.");
			return;
		}
		
		if (!PetNameTable.getInstance().isValidPetName(_name))
		{
			player.sendPacket(SystemMessageId.AN_INVALID_CHARACTER_IS_INCLUDED_IN_THE_PET_S_NAME);
			return;
		}
		
		pet.setName(_name);
		pet.updateAndBroadcastStatus(1);
	}
}
