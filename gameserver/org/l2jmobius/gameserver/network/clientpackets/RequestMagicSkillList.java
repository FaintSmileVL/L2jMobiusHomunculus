package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author UnAfraid
 */
public class RequestMagicSkillList implements IClientIncomingPacket
{
	private int _objectId;
	@SuppressWarnings("unused")
	private int _charId;
	@SuppressWarnings("unused")
	private int _unk;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		_charId = packet.readD();
		_unk = packet.readD();
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
		
		if (player.getObjectId() != _objectId)
		{
			LOGGER.warning("Player: " + player + " requested " + getClass().getSimpleName() + " with different object id: " + _objectId);
			return;
		}
		
		player.sendSkillList();
	}
}
