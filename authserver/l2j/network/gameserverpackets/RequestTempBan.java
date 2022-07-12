package l2j.network.gameserverpackets;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import l2j.data.DatabaseFactory;
import network.BaseRecievePacket;
import l2j.LoginController;

/**
 * @author mrTJO
 */
public class RequestTempBan extends BaseRecievePacket
{
	private static final Logger LOGGER = Logger.getLogger(RequestTempBan.class.getName());
	
	private final String _accountName;
	@SuppressWarnings("unused")
	private String _banReason;
	private final String _ip;
	long _banTime;
	
	/**
	 * @param decrypt
	 */
	public RequestTempBan(byte[] decrypt)
	{
		super(decrypt);
		_accountName = readS();
		_ip = readS();
		_banTime = readQ();
		final boolean haveReason = readC() != 0;
		if (haveReason)
		{
			_banReason = readS();
		}
		banUser();
	}
	
	private void banUser()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO account_data VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE value=?"))
		{
			ps.setString(1, _accountName);
			ps.setString(2, "ban_temp");
			ps.setString(3, Long.toString(_banTime));
			ps.setString(4, Long.toString(_banTime));
			ps.execute();
		}
		catch (SQLException e)
		{
			LOGGER.warning(getClass().getSimpleName() + ": " + e.getMessage());
		}
		
		try
		{
			LoginController.getInstance().addBanForAddress(_ip, _banTime);
		}
		catch (UnknownHostException e)
		{
			// Ignore.
		}
	}
}
