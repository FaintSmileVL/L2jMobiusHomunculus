package org.l2jmobius.gameserver.model.zone.type;

import java.lang.ref.WeakReference;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import org.l2jmobius.gameserver.model.PlayerCondOverride;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.fishing.Fishing;
import org.l2jmobius.gameserver.model.zone.ZoneId;
import org.l2jmobius.gameserver.model.zone.ZoneType;
import org.l2jmobius.gameserver.network.serverpackets.fishing.ExAutoFishAvailable;

/**
 * A fishing zone
 * @author durgus
 */
public class FishingZone extends ZoneType
{
	public FishingZone(int id)
	{
		super(id);
	}
	
	@Override
	protected void onEnter(Creature creature)
	{
		if (creature.isPlayer())
		{
			if ((Config.ALLOW_FISHING || creature.canOverrideCond(PlayerCondOverride.ZONE_CONDITIONS)) && !creature.isInsideZone(ZoneId.FISHING))
			{
				final WeakReference<PlayerInstance> weakPlayer = new WeakReference<>(creature.getActingPlayer());
				ThreadPool.execute(new Runnable()
				{
					@Override
					public void run()
					{
						final PlayerInstance player = weakPlayer.get();
						if (player != null)
						{
							final Fishing fishing = player.getFishing();
							if (player.isInsideZone(ZoneId.FISHING))
							{
								if (fishing.canFish() && !fishing.isFishing())
								{
									if (fishing.isAtValidLocation())
									{
										player.sendPacket(ExAutoFishAvailable.YES);
									}
									else
									{
										player.sendPacket(ExAutoFishAvailable.NO);
									}
								}
								ThreadPool.schedule(this, 1500);
							}
							else
							{
								player.sendPacket(ExAutoFishAvailable.NO);
							}
						}
					}
				});
			}
			creature.setInsideZone(ZoneId.FISHING, true);
		}
	}
	
	@Override
	protected void onExit(Creature creature)
	{
		if (creature.isPlayer())
		{
			creature.setInsideZone(ZoneId.FISHING, false);
			creature.sendPacket(ExAutoFishAvailable.NO);
		}
	}
	
	/*
	 * getWaterZ() this added function returns the Z value for the water surface. In effect this simply returns the upper Z value of the zone. This required some modification of ZoneForm, and zone form extensions.
	 */
	public int getWaterZ()
	{
		return getZone().getHighZ();
	}
}
