package l2j.network.gameserverpackets;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Collection;
import java.util.logging.Logger;

import l2j.data.DatabaseFactory;
import network.BaseRecievePacket;
import l2j.GameServerTable;
import l2j.GameServerInfo;
import l2j.GameServerThread;

/**
 * @author Nik
 */
public class ChangePassword extends BaseRecievePacket
{
	protected static final Logger LOGGER = Logger.getLogger(ChangePassword.class.getName());
	private static GameServerThread gst = null;
	
	public ChangePassword(byte[] decrypt)
	{
		super(decrypt);
		
		final String accountName = readS();
		final String characterName = readS();
		final String curpass = readS();
		final String newpass = readS();
		
		// get the GameServerThread
		final Collection<GameServerInfo> serverList = GameServerTable.getInstance().getRegisteredGameServers().values();
		for (GameServerInfo gsi : serverList)
		{
			if ((gsi.getGameServerThread() != null) && gsi.getGameServerThread().hasAccountOnGameServer(accountName))
			{
				gst = gsi.getGameServerThread();
			}
		}
		
		if (gst == null)
		{
			return;
		}
		
		if ((curpass == null) || (newpass == null))
		{
			gst.ChangePasswordResponse((byte) 0, characterName, "Invalid password data! Try again.");
		}
		else
		{
			try
			{
				final MessageDigest md = MessageDigest.getInstance("SHA");
				final byte[] raw = md.digest(curpass.getBytes(StandardCharsets.UTF_8));
				final String curpassEnc = Base64.getEncoder().encodeToString(raw);
				String pass = null;
				int passUpdated = 0;
				
				// SQL connection
				try (Connection con = DatabaseFactory.getConnection();
					PreparedStatement ps = con.prepareStatement("SELECT password FROM accounts WHERE login=?"))
				{
					ps.setString(1, accountName);
					try (ResultSet rs = ps.executeQuery())
					{
						if (rs.next())
						{
							pass = rs.getString("password");
						}
					}
				}
				
				if (curpassEnc.equals(pass))
				{
					final byte[] password = md.digest(newpass.getBytes(StandardCharsets.UTF_8));
					// SQL connection
					try (Connection con = DatabaseFactory.getConnection();
						PreparedStatement ps = con.prepareStatement("UPDATE accounts SET password=? WHERE login=?"))
					{
						ps.setString(1, Base64.getEncoder().encodeToString(password));
						ps.setString(2, accountName);
						passUpdated = ps.executeUpdate();
					}
					
					LOGGER.info("The password for account " + accountName + " has been changed from " + curpassEnc + " to " + Base64.getEncoder().encodeToString(password));
					if (passUpdated > 0)
					{
						gst.ChangePasswordResponse((byte) 1, characterName, "You have successfully changed your password!");
					}
					else
					{
						gst.ChangePasswordResponse((byte) 0, characterName, "The password change was unsuccessful!");
					}
				}
				else
				{
					gst.ChangePasswordResponse((byte) 0, characterName, "The typed current password doesn't match with your current one.");
				}
			}
			catch (Exception e)
			{
				LOGGER.warning("Error while changing password for account " + accountName + " requested by player " + characterName + "! " + e);
			}
		}
	}
}