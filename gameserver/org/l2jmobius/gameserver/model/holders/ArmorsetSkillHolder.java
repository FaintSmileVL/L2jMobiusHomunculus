package org.l2jmobius.gameserver.model.holders;

import java.util.function.Function;

import org.l2jmobius.gameserver.model.ArmorSet;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author UnAfraid
 */
public class ArmorsetSkillHolder extends SkillHolder
{
	private final int _minimumPieces;
	private final int _minEnchant;
	private final int _artifactSlotMask;
	private final int _artifactBookSlot;
	private final boolean _isOptional;
	
	public ArmorsetSkillHolder(int skillId, int skillLevel, int minimumPieces, int minEnchant, boolean isOptional, int artifactSlotMask, int artifactBookSlot)
	{
		super(skillId, skillLevel);
		_minimumPieces = minimumPieces;
		_minEnchant = minEnchant;
		_isOptional = isOptional;
		_artifactSlotMask = artifactSlotMask;
		_artifactBookSlot = artifactBookSlot;
	}
	
	public int getMinimumPieces()
	{
		return _minimumPieces;
	}
	
	public int getMinEnchant()
	{
		return _minEnchant;
	}
	
	public boolean isOptional()
	{
		return _isOptional;
	}
	
	public boolean validateConditions(PlayerInstance player, ArmorSet armorSet, Function<ItemInstance, Integer> idProvider)
	{
		// Player's doesn't have full busy (1 of 3) artifact real slot
		if (_artifactSlotMask > armorSet.getArtifactSlotMask(player, _artifactBookSlot))
		{
			return false;
		}
		
		// Player doesn't have enough items equipped to use this skill
		if (_minimumPieces > armorSet.getPiecesCount(player, idProvider))
		{
			return false;
		}
		
		// Player's set enchantment isn't enough to use this skill
		if (_minEnchant > armorSet.getLowestSetEnchant(player))
		{
			return false;
		}
		
		// Player doesn't have the required item to use this skill
		if (_isOptional && !armorSet.hasOptionalEquipped(player, idProvider))
		{
			return false;
		}
		
		// Player already knows that skill
		if (player.getSkillLevel(getSkillId()) == getSkillLevel())
		{
			return false;
		}
		
		return true;
	}
}
