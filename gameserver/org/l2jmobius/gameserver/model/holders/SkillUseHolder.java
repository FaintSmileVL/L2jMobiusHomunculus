package org.l2jmobius.gameserver.model.holders;

import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.skills.Skill;

/**
 * @author UnAfraid
 */
public class SkillUseHolder extends SkillHolder
{
	private final ItemInstance _item;
	private final boolean _ctrlPressed;
	private final boolean _shiftPressed;
	
	public SkillUseHolder(Skill skill, ItemInstance item, boolean ctrlPressed, boolean shiftPressed)
	{
		super(skill);
		_item = item;
		_ctrlPressed = ctrlPressed;
		_shiftPressed = shiftPressed;
	}
	
	public ItemInstance getItem()
	{
		return _item;
	}
	
	public boolean isCtrlPressed()
	{
		return _ctrlPressed;
	}
	
	public boolean isShiftPressed()
	{
		return _shiftPressed;
	}
}
