package util;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread to check for deadlocked threads.
 * @author -Nemesiss- L2M
 */
public class DeadLockDetector extends Thread
{
	private static final Logger LOGGER = Logger.getLogger(DeadLockDetector.class.getName());
	public static final String EOL = System.lineSeparator();
	
	private final Duration _checkInterval;
	private final Runnable _deadLockCallback;
	private final ThreadMXBean tmx;
	
	public DeadLockDetector(Duration checkInterval, Runnable deadLockCallback)
	{
		super("DeadLockDetector");
		_checkInterval = checkInterval;
		_deadLockCallback = deadLockCallback;
		tmx = ManagementFactory.getThreadMXBean();
	}
	
	@Override
	public void run()
	{
		boolean deadlock = false;
		while (!deadlock)
		{
			try
			{
				final long[] ids = tmx.findDeadlockedThreads();
				if (ids != null)
				{
					deadlock = true;
					final ThreadInfo[] tis = tmx.getThreadInfo(ids, true, true);
					final StringBuilder info = new StringBuilder();
					info.append("DeadLock Found!");
					info.append(EOL);
					for (ThreadInfo ti : tis)
					{
						info.append(ti.toString());
					}
					
					for (ThreadInfo ti : tis)
					{
						final LockInfo[] locks = ti.getLockedSynchronizers();
						final MonitorInfo[] monitors = ti.getLockedMonitors();
						if ((locks.length == 0) && (monitors.length == 0))
						{
							continue;
						}
						
						ThreadInfo dl = ti;
						info.append("Java-level deadlock:");
						info.append(EOL);
						info.append('\t');
						info.append(dl.getThreadName());
						info.append(" is waiting to lock ");
						info.append(dl.getLockInfo().toString());
						info.append(" which is held by ");
						info.append(dl.getLockOwnerName());
						info.append(EOL);
						while ((dl = tmx.getThreadInfo(new long[]
						{
							dl.getLockOwnerId()
						}, true, true)[0]).getThreadId() != ti.getThreadId())
						{
							info.append('\t');
							info.append(dl.getThreadName());
							info.append(" is waiting to lock ");
							info.append(dl.getLockInfo().toString());
							info.append(" which is held by ");
							info.append(dl.getLockOwnerName());
							info.append(EOL);
						}
					}
					
					LOGGER.warning(info.toString());
					
					if (_deadLockCallback != null)
					{
						_deadLockCallback.run();
					}
				}
				Thread.sleep(_checkInterval.toMillis());
			}
			catch (Exception e)
			{
				LOGGER.log(Level.WARNING, "DeadLockDetector: ", e);
			}
		}
	}
}
