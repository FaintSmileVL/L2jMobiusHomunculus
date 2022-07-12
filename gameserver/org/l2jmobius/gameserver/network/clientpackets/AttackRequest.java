package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.PrivateStoreType;
import org.l2jmobius.gameserver.model.PlayerCondOverride;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.effects.AbstractEffect;
import org.l2jmobius.gameserver.model.skills.AbnormalType;
import org.l2jmobius.gameserver.model.skills.BuffInfo;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

public class AttackRequest implements IClientIncomingPacket
{
	// cddddc
	private int _objectId;
	@SuppressWarnings("unused")
	private int _originX;
	@SuppressWarnings("unused")
	private int _originY;
	@SuppressWarnings("unused")
	private int _originZ;
	@SuppressWarnings("unused")
	private int _attackId;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_objectId = packet.readD();
		_originX = packet.readD();
		_originY = packet.readD();
		_originZ = packet.readD();
		_attackId = packet.readC(); // 0 for simple click 1 for shift-click
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
		
		final BuffInfo info = player.getEffectList().getFirstBuffInfoByAbnormalType(AbnormalType.BOT_PENALTY);
		if (info != null)
		{
			for (AbstractEffect effect : info.getEffects())
			{
				if (!effect.checkCondition(-1))
				{
					player.sendPacket(SystemMessageId.YOU_HAVE_BEEN_REPORTED_AS_AN_ILLEGAL_PROGRAM_USER_SO_YOUR_ACTIONS_HAVE_BEEN_RESTRICTED);
					player.sendPacket(ActionFailed.STATIC_PACKET);
					return;
				}
			}
		}
		
		// avoid using expensive operations if not needed
		final WorldObject target;
		if (player.getTargetId() == _objectId)
		{
			target = player.getTarget();
		}
		else
		{
			target = World.getInstance().findObject(_objectId);
		}
		
		if (target == null)
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		else if ((!target.isTargetable() || player.isTargetingDisabled()) && !player.canOverrideCond(PlayerCondOverride.TARGET_ALL))
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		// Players can't attack objects in the other instances
		else if (target.getInstanceWorld() != player.getInstanceWorld())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		// Only GMs can directly attack invisible characters
		else if (!target.isVisibleFor(player))
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.getTarget() != target)
		{
			target.onAction(player);
		}
		else if ((target.getObjectId() != player.getObjectId()) && (player.getPrivateStoreType() == PrivateStoreType.NONE) && (player.getActiveRequester() == null))
		{
			target.onForcedAttack(player);
		}
		else
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}
}
