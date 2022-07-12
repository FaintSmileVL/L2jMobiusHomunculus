package l2j;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import database.DatabaseBackup;
import l2j.data.DatabaseFactory;
import l2j.network.ClientNetworkManager;
import l2j.network.gameserverpackets.ServerStatus;
import l2j.ui.Gui;

/**
 * @author KenM
 */
public class LoginServer
{
	public Logger LOGGER = Logger.getLogger(LoginServer.class.getName());
	
	public static final int PROTOCOL_REV = 0x0106;
	private static LoginServer INSTANCE;
	private GameServerListener _gameServerListener;
	private Thread _restartLoginServer;
	private static int _loginStatus = ServerStatus.STATUS_NORMAL;
	
	public static void main(String[] args) throws Exception
	{
		INSTANCE = new LoginServer();
	}
	
	public static LoginServer getInstance()
	{
		return INSTANCE;
	}
	
	private LoginServer() throws Exception
	{
		// GUI
		if (!GraphicsEnvironment.isHeadless())
		{
			System.out.println("LoginServer: Running in GUI mode.");
			new Gui();
		}
		
		// Create log folder
		final File logFolder = new File("", "log");
		logFolder.mkdir();
		
		// Create input stream for log file -- or store file data into memory
		
		try (InputStream is = new FileInputStream(new File("./log.cfg")))
		{
			LogManager.getLogManager().readConfiguration(is);
		}
		catch (IOException e)
		{
			LOGGER.warning(getClass().getSimpleName() + ": " + e.getMessage());
		}
		
		// Load Config
		AuthConfig.load();
		
		// Prepare Database
		DatabaseFactory.init();
		
		try
		{
			LoginController.load();
		}
		catch (GeneralSecurityException e)
		{
			LOGGER.log(Level.SEVERE, "FATAL: Failed initializing LoginController. Reason: " + e.getMessage(), e);
			System.exit(1);
		}
		
		GameServerTable.getInstance();
		
		loadBanFile();
		
		if (AuthConfig.LOGIN_SERVER_SCHEDULE_RESTART)
		{
			LOGGER.info("Scheduled LS restart after " + AuthConfig.LOGIN_SERVER_SCHEDULE_RESTART_TIME + " hours");
			_restartLoginServer = new LoginServerRestart();
			_restartLoginServer.setDaemon(true);
			_restartLoginServer.start();
		}
		
		try
		{
			_gameServerListener = new GameServerListener();
			_gameServerListener.start();
			LOGGER.info("Listening for GameServers on " + AuthConfig.GAME_SERVER_LOGIN_HOST + ":" + AuthConfig.GAME_SERVER_LOGIN_PORT);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.SEVERE, "FATAL: Failed to start the Game Server Listener. Reason: " + e.getMessage(), e);
			System.exit(1);
		}
		
		ClientNetworkManager.getInstance().start();
	}
	
	public GameServerListener getGameServerListener()
	{
		return _gameServerListener;
	}
	
	public void loadBanFile()
	{
		final File bannedFile = new File("./banned_ip.cfg");
		if (bannedFile.exists() && bannedFile.isFile())
		{
			try (FileInputStream fis = new FileInputStream(bannedFile);
				InputStreamReader is = new InputStreamReader(fis);
				LineNumberReader lnr = new LineNumberReader(is))
			{
				//@formatter:off
				lnr.lines()
					.map(String::trim)
					.filter(l -> !l.isEmpty() && (l.charAt(0) != '#'))
					.forEach(lineValue ->
					{
						String line = lineValue; 
						String[] parts = line.split("#", 2); // address[ duration][ # comments]
						line = parts[0];
						parts = line.split("\\s+"); // durations might be aligned via multiple spaces
						final String address = parts[0];
						long duration = 0;
						if (parts.length > 1)
						{
							try
							{
								duration = Long.parseLong(parts[1]);
							}
							catch (NumberFormatException nfe)
							{
								LOGGER.warning("Skipped: Incorrect ban duration (" + parts[1] + ") on (" + bannedFile.getName() + "). Line: " + lnr.getLineNumber());
								return;
							}
						}
						
						try
						{
							LoginController.getInstance().addBanForAddress(address, duration);
						}
						catch (UnknownHostException e)
						{
							LOGGER.warning("Skipped: Invalid address (" + address + ") on (" + bannedFile.getName() + "). Line: " + lnr.getLineNumber());
						}
					});
				//@formatter:on
			}
			catch (IOException e)
			{
				LOGGER.log(Level.WARNING, "Error while reading the bans file (" + bannedFile.getName() + "). Details: " + e.getMessage(), e);
			}
			LOGGER.info("Loaded " + LoginController.getInstance().getBannedIps().size() + " IP Bans.");
		}
		else
		{
			LOGGER.warning("IP Bans file (" + bannedFile.getName() + ") is missing or is a directory, skipped.");
		}
	}
	
	class LoginServerRestart extends Thread
	{
		public LoginServerRestart()
		{
			setName("LoginServerRestart");
		}
		
		@Override
		public void run()
		{
			while (!isInterrupted())
			{
				try
				{
					Thread.sleep(AuthConfig.LOGIN_SERVER_SCHEDULE_RESTART_TIME * 3600000);
				}
				catch (InterruptedException e)
				{
					return;
				}
				shutdown(true);
			}
		}
	}
	
	public void shutdown(boolean restart)
	{
		if (AuthConfig.BACKUP_DATABASE)
		{
			DatabaseBackup.performBackup(AuthConfig.BACKUP_DAYS, AuthConfig.BACKUP_PATH, AuthConfig.MYSQL_BIN_PATH, AuthConfig.DATABASE_LOGIN, AuthConfig.DATABASE_PASSWORD, AuthConfig.DATABASE_URL, "login");
		}
		Runtime.getRuntime().exit(restart ? 2 : 0);
	}
	
	public int getStatus()
	{
		return _loginStatus;
	}
	
	public void setStatus(int status)
	{
		_loginStatus = status;
	}
}
