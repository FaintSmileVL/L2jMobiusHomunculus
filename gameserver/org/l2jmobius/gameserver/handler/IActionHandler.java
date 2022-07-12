package org.l2jmobius.gameserver.handler;

import java.util.logging.Logger;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.WorldObject;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

public interface IActionHandler
{
	Logger LOGGER = Logger.getLogger(IActionHandler.class.getName());
	
	boolean action(PlayerInstance player, WorldObject target, boolean interact);
	
	InstanceType getInstanceType();
}