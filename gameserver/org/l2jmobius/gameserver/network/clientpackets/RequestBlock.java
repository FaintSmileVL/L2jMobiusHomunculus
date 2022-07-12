package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.data.sql.CharNameTable;
import org.l2jmobius.gameserver.data.xml.FakePlayerData;
import org.l2jmobius.gameserver.model.BlockList;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

public class RequestBlock implements IClientIncomingPacket
{
	private static final int BLOCK = 0;
	private static final int UNBLOCK = 1;
	private static final int BLOCKLIST = 2;
	private static final int ALLBLOCK = 3;
	private static final int ALLUNBLOCK = 4;
	
	private String _name;
	private Integer _type;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_type = packet.readD(); // 0x00 - block, 0x01 - unblock, 0x03 - allblock, 0x04 - allunblock
		if ((_type == BLOCK) || (_type == UNBLOCK))
		{
			_name = packet.readS();
		}
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		final int targetId = CharNameTable.getInstance().getIdByName(_name);
		final int targetAL = CharNameTable.getInstance().getAccessLevelById(targetId);
		if (player == null)
		{
			return;
		}
		
		switch (_type)
		{
			case BLOCK:
			case UNBLOCK:
			{
				// TODO: Save in database? :P
				if (FakePlayerData.getInstance().isTalkable(_name))
				{
					if (_type == BLOCK)
					{
						final SystemMessage sm = new SystemMessage(SystemMessageId.S1_HAS_BEEN_ADDED_TO_YOUR_IGNORE_LIST);
						sm.addString(FakePlayerData.getInstance().getProperName(_name));
						player.sendPacket(sm);
					}
					else
					{
						final SystemMessage sm = new SystemMessage(SystemMessageId.S1_HAS_BEEN_REMOVED_FROM_YOUR_IGNORE_LIST);
						sm.addString(FakePlayerData.getInstance().getProperName(_name));
						player.sendPacket(sm);
					}
					return;
				}
				
				// can't use block/unblock for locating invisible characters
				if (targetId <= 0)
				{
					// Incorrect player name.
					player.sendPacket(SystemMessageId.YOU_HAVE_FAILED_TO_REGISTER_THE_USER_TO_YOUR_IGNORE_LIST);
					return;
				}
				
				if (targetAL > 0)
				{
					// Cannot block a GM character.
					player.sendPacket(SystemMessageId.YOU_MAY_NOT_IMPOSE_A_BLOCK_ON_A_GM);
					return;
				}
				
				if (player.getObjectId() == targetId)
				{
					return;
				}
				
				if (_type == BLOCK)
				{
					BlockList.addToBlockList(player, targetId);
				}
				else
				{
					BlockList.removeFromBlockList(player, targetId);
				}
				break;
			}
			case BLOCKLIST:
			{
				BlockList.sendListToOwner(player);
				break;
			}
			case ALLBLOCK:
			{
				player.sendPacket(SystemMessageId.MESSAGE_REFUSAL_MODE);
				BlockList.setBlockAll(player, true);
				break;
			}
			case ALLUNBLOCK:
			{
				player.sendPacket(SystemMessageId.MESSAGE_ACCEPTANCE_MODE);
				BlockList.setBlockAll(player, false);
				break;
			}
			default:
			{
				LOGGER.info("Unknown 0xA9 block type: " + _type);
			}
		}
	}
}
