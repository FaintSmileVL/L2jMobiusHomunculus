package org.l2jmobius.gameserver.handler;

import java.util.EnumMap;
import java.util.Map;

import org.l2jmobius.gameserver.model.punishment.PunishmentType;

/**
 * This class manages handlers of punishments.
 * @author UnAfraid
 */
public class PunishmentHandler implements IHandler<IPunishmentHandler, PunishmentType>
{
	private final Map<PunishmentType, IPunishmentHandler> _handlers = new EnumMap<>(PunishmentType.class);
	
	protected PunishmentHandler()
	{
	}
	
	@Override
	public void registerHandler(IPunishmentHandler handler)
	{
		_handlers.put(handler.getType(), handler);
	}
	
	@Override
	public synchronized void removeHandler(IPunishmentHandler handler)
	{
		_handlers.remove(handler.getType());
	}
	
	@Override
	public IPunishmentHandler getHandler(PunishmentType val)
	{
		return _handlers.get(val);
	}
	
	@Override
	public int size()
	{
		return _handlers.size();
	}
	
	public static PunishmentHandler getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final PunishmentHandler INSTANCE = new PunishmentHandler();
	}
}
