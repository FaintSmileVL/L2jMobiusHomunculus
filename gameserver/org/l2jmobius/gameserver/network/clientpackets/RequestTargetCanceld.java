package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.TargetUnselected;

/**
 * @author Mobius
 */
public class RequestTargetCanceld implements IClientIncomingPacket
{
	private boolean _targetLost;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_targetLost = packet.readH() != 0;
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
		
		if (player.isLockedTarget())
		{
			player.sendPacket(SystemMessageId.FAILED_TO_REMOVE_ENMITY);
			return;
		}
		
		if (player.isCastingNow())
		{
			player.abortAllSkillCasters();
		}
		
		if (_targetLost)
		{
			player.setTarget(null);
		}
		
		if (player.isInAirShip())
		{
			player.broadcastPacket(new TargetUnselected(player));
		}
	}
}
