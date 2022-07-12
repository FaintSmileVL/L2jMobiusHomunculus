package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.instancemanager.CastleManager;
import org.l2jmobius.gameserver.instancemanager.FortManager;
import org.l2jmobius.gameserver.model.SiegeClan;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.siege.Castle;
import org.l2jmobius.gameserver.model.siege.Fort;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class Die implements IClientOutgoingPacket
{
	private final int _objectId;
	private final boolean _isSweepable;
	private int _flags = 0;
	
	public Die(Creature creature)
	{
		_objectId = creature.getObjectId();
		_isSweepable = creature.isAttackable() && creature.isSweepActive();
		if (creature.isPlayer())
		{
			final Clan clan = creature.getActingPlayer().getClan();
			boolean isInCastleDefense = false;
			boolean isInFortDefense = false;
			SiegeClan siegeClan = null;
			final Castle castle = CastleManager.getInstance().getCastle(creature);
			final Fort fort = FortManager.getInstance().getFort(creature);
			if ((castle != null) && castle.getSiege().isInProgress())
			{
				siegeClan = castle.getSiege().getAttackerClan(clan);
				isInCastleDefense = (siegeClan == null) && castle.getSiege().checkIsDefender(clan);
			}
			else if ((fort != null) && fort.getSiege().isInProgress())
			{
				siegeClan = fort.getSiege().getAttackerClan(clan);
				isInFortDefense = (siegeClan == null) && fort.getSiege().checkIsDefender(clan);
			}
			
			// ClanHall check.
			if ((clan != null) && (clan.getHideoutId() > 0))
			{
				_flags += 2;
			}
			// Castle check.
			if (((clan != null) && (clan.getCastleId() > 0)) || isInCastleDefense)
			{
				_flags += 4;
			}
			// Fortress check.
			if (((clan != null) && (clan.getFortId() > 0)) || isInFortDefense)
			{
				_flags += 8;
			}
			// Outpost check.
			if (((siegeClan != null) && !isInCastleDefense && !isInFortDefense && !siegeClan.getFlag().isEmpty()))
			{
				_flags += 16;
			}
			// Feather check.
			if (creature.getAccessLevel().allowFixedRes() || creature.getInventory().haveItemForSelfResurrection())
			{
				_flags += 32;
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.DIE.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeC(_flags);
		packet.writeC(0);
		packet.writeC(_isSweepable ? 1 : 0);
		packet.writeC(0); // 1: Resurrection during siege.
		return true;
	}
}
