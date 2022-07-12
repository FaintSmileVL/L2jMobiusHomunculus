package org.l2jmobius.gameserver.model.events.returns;

/**
 * @author UnAfraid
 */
public class TerminateReturn extends AbstractEventReturn
{
	private final boolean _terminate;
	
	public TerminateReturn(boolean terminate, boolean override, boolean abort)
	{
		super(override, abort);
		_terminate = terminate;
	}
	
	/**
	 * @return {@code true} if execution has to be terminated, {@code false} otherwise.
	 */
	public boolean terminate()
	{
		return _terminate;
	}
}
