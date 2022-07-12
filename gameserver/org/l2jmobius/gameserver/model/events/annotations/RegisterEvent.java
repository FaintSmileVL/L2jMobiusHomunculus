package org.l2jmobius.gameserver.model.events.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.l2jmobius.gameserver.model.events.EventType;

/**
 * @author UnAfraid
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RegisterEvent
{
	EventType value();
}
