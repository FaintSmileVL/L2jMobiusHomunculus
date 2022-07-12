package org.l2jmobius.tools.dbinstaller;

import java.awt.HeadlessException;

import javax.swing.UIManager;

import org.l2jmobius.tools.dbinstaller.console.DBInstallerConsole;
import org.l2jmobius.tools.dbinstaller.gui.DBConfigGUI;

/**
 * Contains main class for Database Installer If system doesn't support the graphical UI, start the installer in console mode.
 * @author mrTJO
 */
public class LauncherGS extends AbstractDBLauncher
{
	public static void main(String[] args) throws Exception
	{
		final String defDatabase = "l2jmobius";
		final String dir = "sql/game/";
		
		if ((args != null) && (args.length > 0))
		{
			new DBInstallerConsole(defDatabase, dir, getArg("-h", args), getArg("-p", args), getArg("-u", args), getArg("-pw", args), getArg("-d", args), getArg("-m", args));
			return;
		}
		
		try
		{
			// Set OS Look And Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			// Ignore.
		}
		
		try
		{
			new DBConfigGUI(defDatabase, dir);
		}
		catch (HeadlessException e)
		{
			new DBInstallerConsole(defDatabase, dir);
		}
	}
}
