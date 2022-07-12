package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.Config;
import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.instancemanager.BoatManager;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.BoatInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.type.WeaponType;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.MoveToLocationInVehicle;
import org.l2jmobius.gameserver.network.serverpackets.StopMoveInVehicle;

public class RequestMoveToLocationInVehicle implements IClientIncomingPacket
{
	private int _boatId;
	private int _targetX;
	private int _targetY;
	private int _targetZ;
	private int _originX;
	private int _originY;
	private int _originZ;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_boatId = packet.readD(); // objectId of boat
		_targetX = packet.readD();
		_targetY = packet.readD();
		_targetZ = packet.readD();
		_originX = packet.readD();
		_originY = packet.readD();
		_originZ = packet.readD();
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
		
		if ((Config.PLAYER_MOVEMENT_BLOCK_TIME > 0) && !player.isGM() && (player.getNotMoveUntil() > Chronos.currentTimeMillis()))
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_MOVE_WHILE_SPEAKING_TO_AN_NPC_ONE_MOMENT_PLEASE);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if ((_targetX == _originX) && (_targetY == _originY) && (_targetZ == _originZ))
		{
			client.sendPacket(new StopMoveInVehicle(player, _boatId));
			return;
		}
		
		if (player.isAttackingNow() && (player.getActiveWeaponItem() != null) && (player.getActiveWeaponItem().getItemType() == WeaponType.BOW))
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isSitting() || player.isMovementDisabled())
		{
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.hasSummon())
		{
			client.sendPacket(SystemMessageId.YOU_SHOULD_RELEASE_YOUR_PET_OR_SERVITOR_SO_THAT_IT_DOES_NOT_FALL_OFF_OF_THE_BOAT_AND_DROWN);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isTransformed())
		{
			client.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_WHILE_RIDING_ON_A_BOAT_AIRSHIP_OR_ELEVATOR);
			client.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		BoatInstance boat;
		if (player.isInBoat())
		{
			boat = player.getBoat();
			if (boat.getObjectId() != _boatId)
			{
				boat = BoatManager.getInstance().getBoat(_boatId);
				player.setVehicle(boat);
			}
		}
		else
		{
			boat = BoatManager.getInstance().getBoat(_boatId);
			player.setVehicle(boat);
		}
		
		final Location pos = new Location(_targetX, _targetY, _targetZ);
		final Location originPos = new Location(_originX, _originY, _originZ);
		player.setInVehiclePosition(pos);
		player.broadcastPacket(new MoveToLocationInVehicle(player, pos, originPos));
	}
}
