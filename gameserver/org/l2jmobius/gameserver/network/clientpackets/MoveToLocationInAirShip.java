package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.model.Location;
import org.l2jmobius.gameserver.model.actor.instance.AirShipInstance;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.type.WeaponType;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.ExMoveToLocationInAirShip;
import org.l2jmobius.gameserver.network.serverpackets.StopMoveInVehicle;

/**
 * format: ddddddd X:%d Y:%d Z:%d OriginX:%d OriginY:%d OriginZ:%d
 * @author GodKratos
 */
public class MoveToLocationInAirShip implements IClientIncomingPacket
{
	private int _shipId;
	private int _targetX;
	private int _targetY;
	private int _targetZ;
	private int _originX;
	private int _originY;
	private int _originZ;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_shipId = packet.readD();
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
		
		if ((_targetX == _originX) && (_targetY == _originY) && (_targetZ == _originZ))
		{
			player.sendPacket(new StopMoveInVehicle(player, _shipId));
			return;
		}
		
		if (player.isAttackingNow() && (player.getActiveWeaponItem() != null) && (player.getActiveWeaponItem().getItemType() == WeaponType.BOW))
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (player.isSitting() || player.isMovementDisabled())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!player.isInAirShip())
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		final AirShipInstance airShip = player.getAirShip();
		if (airShip.getObjectId() != _shipId)
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.setInVehiclePosition(new Location(_targetX, _targetY, _targetZ));
		player.broadcastPacket(new ExMoveToLocationInAirShip(player));
	}
}
