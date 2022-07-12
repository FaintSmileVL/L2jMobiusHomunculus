package org.l2jmobius.gameserver.model.eventengine.drop;

import java.util.function.Supplier;

/**
 * @author UnAfraid
 */
public enum EventDrops
{
	GROUPED(GroupedDrop::new),
	NORMAL(NormalDrop::new);
	
	private final Supplier<? extends IEventDrop> _supplier;
	
	private EventDrops(Supplier<IEventDrop> supplier)
	{
		_supplier = supplier;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IEventDrop> T newInstance()
	{
		return (T) (_supplier.get());
	}
}
