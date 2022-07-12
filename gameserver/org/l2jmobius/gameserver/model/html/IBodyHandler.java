package org.l2jmobius.gameserver.model.html;

import java.util.Collection;

/**
 * @author UnAfraid
 * @param <T>
 */
@FunctionalInterface
public interface IBodyHandler<T>
{
	void apply(int pages, T type, StringBuilder sb);
	
	default void create(Collection<T> elements, int pages, int start, int elementsPerPage, StringBuilder sb)
	{
		int i = 0;
		for (T element : elements)
		{
			if (i++ < start)
			{
				continue;
			}
			
			apply(pages, element, sb);
			
			if (i >= (elementsPerPage + start))
			{
				break;
			}
		}
	}
}