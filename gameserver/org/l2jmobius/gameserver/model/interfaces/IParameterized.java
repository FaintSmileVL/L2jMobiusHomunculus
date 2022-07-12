package org.l2jmobius.gameserver.model.interfaces;

/**
 * @author UnAfraid
 * @param <T>
 */
public interface IParameterized<T>
{
	T getParameters();
	
	void setParameters(T set);
}
