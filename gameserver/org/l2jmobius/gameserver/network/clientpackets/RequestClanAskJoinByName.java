package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.AskJoinPledge;

/**
 * @author Mobius
 */
public class RequestClanAskJoinByName implements IClientIncomingPacket
{
	private String _playerName;
	private int _pledgeType;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_playerName = packet.readS();
		_pledgeType = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getClan() == null))
		{
			return;
		}
		
		final PlayerInstance invitedPlayer = World.getInstance().getPlayer(_playerName);
		if (!player.getClan().checkClanJoinCondition(player, invitedPlayer, _pledgeType))
		{
			return;
		}
		if (!player.getRequest().setRequest(invitedPlayer, this))
		{
			return;
		}
		
		invitedPlayer.sendPacket(new AskJoinPledge(player, _pledgeType, player.getClan().getName()));
	}
	
	public int getPledgeType()
	{
		return _pledgeType;
	}
}