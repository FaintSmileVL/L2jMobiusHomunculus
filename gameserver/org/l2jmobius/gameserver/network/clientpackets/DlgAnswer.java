package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.PlayerAction;
import org.l2jmobius.gameserver.handler.AdminCommandHandler;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.creature.player.OnPlayerDlgAnswer;
import org.l2jmobius.gameserver.model.events.returns.TerminateReturn;
import org.l2jmobius.gameserver.model.holders.DoorRequestHolder;
import org.l2jmobius.gameserver.model.holders.SummonRequestHolder;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * @author Dezmond_snz
 */
public class DlgAnswer implements IClientIncomingPacket
{
	private int _messageId;
	private int _answer;
	private int _requesterId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_messageId = packet.readD();
		_answer = packet.readD();
		_requesterId = packet.readD();
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
		
		final TerminateReturn term = EventDispatcher.getInstance().notifyEvent(new OnPlayerDlgAnswer(player, _messageId, _answer, _requesterId), player, TerminateReturn.class);
		if ((term != null) && term.terminate())
		{
			return;
		}
		
		if (_messageId == SystemMessageId.S1_3.getId())
		{
			if (player.removeAction(PlayerAction.ADMIN_COMMAND))
			{
				final String cmd = player.getAdminConfirmCmd();
				player.setAdminConfirmCmd(null);
				if (_answer == 0)
				{
					return;
				}
				
				// The 'useConfirm' must be disabled here, as we don't want to repeat that process.
				AdminCommandHandler.getInstance().useAdminCommand(player, cmd, false);
			}
		}
		else if ((_messageId == SystemMessageId.C1_IS_ATTEMPTING_TO_DO_A_RESURRECTION_THAT_RESTORES_S2_S3_XP_ACCEPT.getId()) || (_messageId == SystemMessageId.YOUR_CHARM_OF_COURAGE_IS_TRYING_TO_RESURRECT_YOU_WOULD_YOU_LIKE_TO_RESURRECT_NOW.getId()))
		{
			player.reviveAnswer(_answer);
		}
		else if (_messageId == SystemMessageId.C1_WISHES_TO_SUMMON_YOU_FROM_S2_DO_YOU_ACCEPT.getId())
		{
			final SummonRequestHolder holder = player.removeScript(SummonRequestHolder.class);
			if ((_answer == 1) && (holder != null) && (holder.getSummoner().getObjectId() == _requesterId))
			{
				player.teleToLocation(holder.getLocation(), true);
			}
		}
		else if (_messageId == SystemMessageId.WOULD_YOU_LIKE_TO_OPEN_THE_GATE.getId())
		{
			final DoorRequestHolder holder = player.removeScript(DoorRequestHolder.class);
			if ((holder != null) && (holder.getDoor() == player.getTarget()) && (_answer == 1))
			{
				holder.getDoor().openMe();
			}
		}
		else if (_messageId == SystemMessageId.WOULD_YOU_LIKE_TO_CLOSE_THE_GATE.getId())
		{
			final DoorRequestHolder holder = player.removeScript(DoorRequestHolder.class);
			if ((holder != null) && (holder.getDoor() == player.getTarget()) && (_answer == 1))
			{
				holder.getDoor().closeMe();
			}
		}
	}
}
