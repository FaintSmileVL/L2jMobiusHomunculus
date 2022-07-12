package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.ai.CtrlEvent;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;

/**
 * @version $Revision: 1.1.2.1.2.4 $ $Date: 2005/03/27 15:29:30 $
 */
public class CannotMoveAnymore implements IClientIncomingPacket
{
	private int _x;
	private int _y;
	private int _z;
	private int _heading;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_x = packet.readD();
		_y = packet.readD();
		_z = packet.readD();
		_heading = packet.readD();
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
		
		if (player.getAI() != null)
		{
			player.getAI().notifyEvent(CtrlEvent.EVT_ARRIVED_BLOCKED, new Location(_x, _y, _z, _heading));
		}
		/*
		 * if (player.getParty() != null) { player.getParty().broadcastToPartyMembers(player, new PartyMemberPosition(player)); }
		 */
		
		// player.stopMove();
		//
		// if (Config.DEBUG)
		// LOGGER.finer("client: x:"+_x+" y:"+_y+" z:"+_z+
		// " server x:"+player.getX()+" y:"+player.getZ()+" z:"+player.getZ());
		// StopMove smwl = new StopMove(player);
		// client.getPlayer().sendPacket(smwl);
		// client.getPlayer().broadcastPacket(smwl);
		//
		// StopRotation sr = new StopRotation(client.getPlayer(),
		// _heading);
		// client.getPlayer().sendPacket(sr);
		// client.getPlayer().broadcastPacket(sr);
	}
}
