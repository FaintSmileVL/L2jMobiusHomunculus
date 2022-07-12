package org.l2jmobius.gameserver.network.clientpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @author Plim
 */
public class RequestPetitionFeedback implements IClientIncomingPacket
{
	private static final String INSERT_FEEDBACK = "INSERT INTO petition_feedback VALUES (?,?,?,?,?)";
	
	// cdds
	// private int _unknown;
	private int _rate; // 4=VeryGood, 3=Good, 2=Fair, 1=Poor, 0=VeryPoor
	private String _message;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		// _unknown =
		packet.readD(); // unknown
		_rate = packet.readD();
		_message = packet.readS();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if ((player == null) || (player.getLastPetitionGmName() == null))
		{
			return;
		}
		
		if ((_rate > 4) || (_rate < 0))
		{
			return;
		}
		
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement statement = con.prepareStatement(INSERT_FEEDBACK))
		{
			statement.setString(1, player.getName());
			statement.setString(2, player.getLastPetitionGmName());
			statement.setInt(3, _rate);
			statement.setString(4, _message);
			statement.setLong(5, Chronos.currentTimeMillis());
			statement.execute();
		}
		catch (SQLException e)
		{
			LOGGER.severe("Error while saving petition feedback");
		}
	}
}
