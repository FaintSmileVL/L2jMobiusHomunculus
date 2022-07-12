package org.l2jmobius.gameserver.model.ensoul;

import java.util.ArrayList;
import java.util.List;

import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.items.type.CrystalType;

/**
 * @author UnAfraid
 */
public class EnsoulFee
{
	private final CrystalType _type;
	
	private final ItemHolder[] _ensoulFee = new ItemHolder[3];
	private final ItemHolder[] _resoulFees = new ItemHolder[3];
	private final List<ItemHolder> _removalFee = new ArrayList<>();
	
	public EnsoulFee(CrystalType type)
	{
		_type = type;
	}
	
	public CrystalType getCrystalType()
	{
		return _type;
	}
	
	public void setEnsoul(int index, ItemHolder item)
	{
		_ensoulFee[index] = item;
	}
	
	public void setResoul(int index, ItemHolder item)
	{
		_resoulFees[index] = item;
	}
	
	public void addRemovalFee(ItemHolder itemHolder)
	{
		_removalFee.add(itemHolder);
	}
	
	public ItemHolder getEnsoul(int index)
	{
		return _ensoulFee[index];
	}
	
	public ItemHolder getResoul(int index)
	{
		return _resoulFees[index];
	}
	
	public List<ItemHolder> getRemovalFee()
	{
		return _removalFee;
	}
}
