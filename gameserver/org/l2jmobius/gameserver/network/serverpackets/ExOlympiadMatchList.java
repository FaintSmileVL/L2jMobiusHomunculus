package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.olympiad.AbstractOlympiadGame;
import org.l2jmobius.gameserver.model.olympiad.OlympiadGameClassed;
import org.l2jmobius.gameserver.model.olympiad.OlympiadGameManager;
import org.l2jmobius.gameserver.model.olympiad.OlympiadGameNonClassed;
import org.l2jmobius.gameserver.model.olympiad.OlympiadGameTask;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author mrTJO
 */
public class ExOlympiadMatchList implements IClientOutgoingPacket
{
	private final List<OlympiadGameTask> _games = new ArrayList<>();
	
	public ExOlympiadMatchList()
	{
		OlympiadGameTask task;
		for (int i = 0; i < OlympiadGameManager.getInstance().getNumberOfStadiums(); i++)
		{
			task = OlympiadGameManager.getInstance().getOlympiadTask(i);
			if (task != null)
			{
				if (!task.isGameStarted() || task.isBattleFinished())
				{
					continue; // initial or finished state not shown
				}
				_games.add(task);
			}
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RECEIVE_OLYMPIAD.writeId(packet);
		
		packet.writeD(0x00); // Type 0 = Match List, 1 = Match Result
		
		packet.writeD(_games.size());
		packet.writeD(0x00);
		
		for (OlympiadGameTask curGame : _games)
		{
			final AbstractOlympiadGame game = curGame.getGame();
			if (game != null)
			{
				packet.writeD(game.getStadiumId()); // Stadium Id (Arena 1 = 0)
				
				if (game instanceof OlympiadGameNonClassed)
				{
					packet.writeD(1);
				}
				else if (game instanceof OlympiadGameClassed)
				{
					packet.writeD(2);
				}
				else
				{
					packet.writeD(0);
				}
				
				packet.writeD(curGame.isRunning() ? 0x02 : 0x01); // (1 = Standby, 2 = Playing)
				packet.writeS(game.getPlayerNames()[0]); // Player 1 Name
				packet.writeS(game.getPlayerNames()[1]); // Player 2 Name
			}
		}
		return true;
	}
}
