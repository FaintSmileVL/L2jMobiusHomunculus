package org.l2jmobius.gameserver.model.siege;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.l2jmobius.gameserver.model.SiegeClan;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;

/**
 * @author JIV
 */
public interface Siegable
{
	void startSiege();
	
	void endSiege();
	
	SiegeClan getAttackerClan(int clanId);
	
	SiegeClan getAttackerClan(Clan clan);
	
	Collection<SiegeClan> getAttackerClans();
	
	List<PlayerInstance> getAttackersInZone();
	
	boolean checkIsAttacker(Clan clan);
	
	SiegeClan getDefenderClan(int clanId);
	
	SiegeClan getDefenderClan(Clan clan);
	
	Collection<SiegeClan> getDefenderClans();
	
	boolean checkIsDefender(Clan clan);
	
	Set<Npc> getFlag(Clan clan);
	
	Calendar getSiegeDate();
	
	boolean giveFame();
	
	int getFameFrequency();
	
	int getFameAmount();
	
	void updateSiege();
}
