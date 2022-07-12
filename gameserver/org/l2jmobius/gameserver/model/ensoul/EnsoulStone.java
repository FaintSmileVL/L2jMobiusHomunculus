package org.l2jmobius.gameserver.model.ensoul;

import java.util.ArrayList;
import java.util.List;

/**
 * @author UnAfraid
 */
public class EnsoulStone
{
	private final int _id;
	private final int _slotType;
	private final List<Integer> _options = new ArrayList<>();
	
	public EnsoulStone(int id, int slotType)
	{
		_id = id;
		_slotType = slotType;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public int getSlotType()
	{
		return _slotType;
	}
	
	public List<Integer> getOptions()
	{
		return _options;
	}
	
	public void addOption(int option)
	{
		_options.add(option);
	}
}
