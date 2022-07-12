package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.ClanEntryStatus;
import org.l2jmobius.gameserver.instancemanager.ClanEntryManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExPledgeRecruitApplyInfo;

/**
 * @author Sdw
 */
public class RequestPledgeRecruitApplyInfo implements IClientIncomingPacket
{
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
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
		
		final ClanEntryStatus status;
		if ((player.getClan() != null) && player.isClanLeader() && ClanEntryManager.getInstance().isClanRegistred(player.getClanId()))
		{
			status = ClanEntryStatus.ORDERED;
		}
		else if ((player.getClan() == null) && (ClanEntryManager.getInstance().isPlayerRegistred(player.getObjectId())))
		{
			status = ClanEntryStatus.WAITING;
		}
		else
		{
			status = ClanEntryStatus.DEFAULT;
		}
		
		player.sendPacket(new ExPledgeRecruitApplyInfo(status));
	}
}
