package org.l2jmobius.gameserver.model.zone.type;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;

/**
 * A Peace Zone
 * @author durgus
 */
public class PeaceZone extends ZoneType
{
	public PeaceZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		if (!isEnabled())
		{
			return;
		}
		
		if (creature.isPlayer())
		{
			final PlayerInstance player = creature.getActingPlayer();
			// PVP possible during siege, now for siege participants only
			// Could also check if this town is in siege, or if any siege is going on
			if ((player.getSiegeState() != 0) && (Config.PEACE_ZONE_MODE == 1))
			{
				return;
			}
		}
		
		if (Config.PEACE_ZONE_MODE != 2)
		{
			creature.setInsideZone(ZoneId.PEACE, true);
		}
		
		if (!getAllowStore())
		{
			creature.setInsideZone(ZoneId.NO_STORE, true);
		}
		
		// Send player info to nearby players.
		if (creature.isPlayer())
		{
			creature.broadcastInfo();
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (Config.PEACE_ZONE_MODE != 2)
		{
			creature.setInsideZone(ZoneId.PEACE, false);
		}
		
		if (!getAllowStore())
		{
			creature.setInsideZone(ZoneId.NO_STORE, false);
		}
		
		// Send player info to nearby players.
		if (creature.isPlayer() && !creature.isTeleporting())
		{
			creature.broadcastInfo();
		}
	}
	
	@Override
	public void setEnabled(boolean value)
	{
		super.setEnabled(value);
		if (value)
		{
			for (PlayerInstance player : World.getInstance().getPlayers())
			{
				if ((player != null) && isInsideZone(player))
				{
					revalidateInZone(player);
					
					if (player.getPet() != null)
					{
						revalidateInZone(player.getPet());
					}
					
					for (Summon summon : player.getServitors().values())
					{
						revalidateInZone(summon);
					}
				}
			}
		}
		else
		{
			for (Creature creature : getCharactersInside())
			{
				if (creature != null)
				{
					removeCharacter(creature);
				}
			}
		}
	}
}
