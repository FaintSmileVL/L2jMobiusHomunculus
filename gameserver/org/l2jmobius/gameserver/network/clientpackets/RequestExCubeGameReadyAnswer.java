package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.HandysBlockCheckerManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * Format: chddd d: Arena d: Answer
 * @author mrTJO
 */
public class RequestExCubeGameReadyAnswer implements IClientIncomingPacket
{
	private int _arena;
	private int _answer;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		// client sends -1,0,1,2 for arena parameter
		_arena = packet.readD() + 1;
		// client sends 1 if clicked confirm on not clicked, 0 if clicked cancel
		_answer = packet.readD();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		switch (_answer)
		{
			case 0:
			{
				// Cancel - Answer No
				break;
			}
			case 1:
			{
				// OK or Time Over
				HandysBlockCheckerManager.getInstance().increaseArenaVotes(_arena);
				break;
			}
			default:
			{
				LOGGER.warning("Unknown Cube Game Answer ID: " + _answer);
				break;
			}
		}
	}
}
