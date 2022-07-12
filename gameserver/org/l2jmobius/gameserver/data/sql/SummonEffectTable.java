package org.l2jmobius.gameserver.data.sql;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author Nyaran
 */
public class SummonEffectTable
{
	/** Servitors **/
	// Map tree
	// -> key: charObjectId, value: classIndex Map
	// --> key: classIndex, value: servitors Map
	// ---> key: servitorSkillId, value: Effects list
	private final Map<Integer, Map<Integer, Map<Integer, Collection<SummonEffect>>>> _servitorEffects = new ConcurrentHashMap<>();
	
	public Map<Integer, Map<Integer, Map<Integer, Collection<SummonEffect>>>> getServitorEffectsOwner()
	{
		return _servitorEffects;
	}
	
	public Map<Integer, Collection<SummonEffect>> getServitorEffects(PlayerInstance owner)
	{
		final Map<Integer, Map<Integer, Collection<SummonEffect>>> servitorMap = _servitorEffects.get(owner.getObjectId());
		if (servitorMap == null)
		{
			return null;
		}
		return servitorMap.get(owner.getClassIndex());
	}
	
	/** Pets **/
	private final Map<Integer, Collection<SummonEffect>> _petEffects = new ConcurrentHashMap<>(); // key: petItemObjectId, value: Effects list
	
	public Map<Integer, Collection<SummonEffect>> getPetEffects()
	{
		return _petEffects;
	}
	
	public static class SummonEffect
	{
		private final Skill _skill;
		private final int _effectCurTime;
		
		public SummonEffect(Skill skill, int effectCurTime)
		{
			_skill = skill;
			_effectCurTime = effectCurTime;
		}
		
		public Skill getSkill()
		{
			return _skill;
		}
		
		public int getEffectCurTime()
		{
			return _effectCurTime;
		}
	}
	
	public static SummonEffectTable getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final SummonEffectTable INSTANCE = new SummonEffectTable();
	}
}
