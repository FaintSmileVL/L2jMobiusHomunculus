package org.l2jmobius.gameserver.network.clientpackets.pledgeV2;

import java.util.Collection;

import org.l2jmobius.gameserver.ThreadPool;
import network.PacketReader;
import org.l2jmobius.gameserver.data.xml.DailyMissionData;
import org.l2jmobius.gameserver.model.DailyMissionDataHolder;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.actor.request.RewardRequest;
import org.l2jmobius.gameserver.network.Disconnection;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.pledgeV2.ExPledgeMissionInfo;
import org.l2jmobius.gameserver.network.serverpackets.pledgeV2.ExPledgeMissionRewardCount;

/**
 * @author Mobius
 */
public class RequestExPledgeMissionReward implements IClientIncomingPacket
{
	private int _id;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_id = packet.readD();
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
		
		if (player.hasRequest(RewardRequest.class))
		{
			LOGGER.warning("Kicked " + player + " for spamming " + getClass().getSimpleName());
			Disconnection.of(player).defaultSequence(true);
			return;
		}
		
		player.addRequest(new RewardRequest(player));
		
		final Collection<DailyMissionDataHolder> reward = DailyMissionData.getInstance().getDailyMissionData(_id);
		if ((reward != null) && !reward.isEmpty())
		{
			reward.stream().filter(o -> o.isDisplayable(player)).forEach(r -> r.requestReward(player));
			client.sendPacket(new ExPledgeMissionRewardCount(player));
			client.sendPacket(new ExPledgeMissionInfo(player));
		}
		
		ThreadPool.schedule(() ->
		{
			player.removeRequest(RewardRequest.class);
		}, 50);
	}
}
