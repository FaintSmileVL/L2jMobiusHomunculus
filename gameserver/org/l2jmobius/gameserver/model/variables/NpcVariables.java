package org.l2jmobius.gameserver.model.variables;

import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * NPC Variables implementation.
 * @author GKR
 */
public class NpcVariables extends AbstractVariables
{
	@Override
	public int getInt(String key)
	{
		return super.getInt(key, 0);
	}
	
	@Override
	public boolean restoreMe()
	{
		return true;
	}
	
	@Override
	public boolean storeMe()
	{
		return true;
	}
	
	@Override
	public boolean deleteMe()
	{
		return true;
	}
	
	/**
	 * Gets the stored player.
	 * @param name the name of the variable
	 * @return the stored player or {@code null}
	 */
	public PlayerInstance getPlayer(String name)
	{
		return getObject(name, PlayerInstance.class);
	}
	
	/**
	 * Gets the stored summon.
	 * @param name the name of the variable
	 * @return the stored summon or {@code null}
	 */
	public Summon getSummon(String name)
	{
		return getObject(name, Summon.class);
	}
}