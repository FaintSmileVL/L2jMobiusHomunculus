package org.l2jmobius.gameserver.model.events.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author UnAfraid
 */
@Repeatable(NpcLevelRanges.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NpcLevelRange
{
	int from();
	
	int to();
}
