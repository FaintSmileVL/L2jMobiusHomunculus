package org.l2jmobius.gameserver.network.clientpackets.commission;

import java.util.function.Predicate;

import network.PacketReader;
import org.l2jmobius.gameserver.instancemanager.CommissionManager;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.commission.CommissionItemType;
import org.l2jmobius.gameserver.model.commission.CommissionTreeType;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.model.items.type.CrystalType;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.commission.ExCloseCommission;

/**
 * @author NosBit
 */
public class RequestCommissionList implements IClientIncomingPacket
{
	private int _treeViewDepth;
	private int _itemType;
	private int _type;
	private int _grade;
	private String _query;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_treeViewDepth = packet.readD();
		_itemType = packet.readD();
		_type = packet.readD();
		_grade = packet.readD();
		_query = packet.readS();
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
		
		if (!CommissionManager.isPlayerAllowedToInteract(player))
		{
			client.sendPacket(ExCloseCommission.STATIC_PACKET);
			return;
		}
		
		Predicate<Item> filter = i -> true;
		switch (_treeViewDepth)
		{
			case 1:
			{
				final CommissionTreeType commissionTreeType = CommissionTreeType.findByClientId(_itemType);
				if (commissionTreeType != null)
				{
					filter = filter.and(i -> commissionTreeType.getCommissionItemTypes().contains(i.getCommissionItemType()));
				}
				break;
			}
			case 2:
			{
				final CommissionItemType commissionItemType = CommissionItemType.findByClientId(_itemType);
				if (commissionItemType != null)
				{
					filter = filter.and(i -> i.getCommissionItemType() == commissionItemType);
				}
				break;
			}
		}
		
		switch (_type)
		{
			case 0: // General
			{
				filter = filter.and(i -> true); // TODO: condition
				break;
			}
			case 1: // Rare
			{
				filter = filter.and(i -> true); // TODO: condition
				break;
			}
		}
		
		switch (_grade)
		{
			case 0:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.NONE);
				break;
			}
			case 1:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.D);
				break;
			}
			case 2:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.C);
				break;
			}
			case 3:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.B);
				break;
			}
			case 4:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.A);
				break;
			}
			case 5:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.S);
				break;
			}
			case 6:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.S80);
				break;
			}
			case 7:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.R);
				break;
			}
			case 8:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.R95);
				break;
			}
			case 9:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.R99);
				break;
			}
			case 10:
			{
				filter = filter.and(i -> i.getCrystalType() == CrystalType.R110);
				break;
			}
		}
		
		filter = filter.and(i -> _query.isEmpty() || i.getName().toLowerCase().contains(_query.toLowerCase()));
		CommissionManager.getInstance().showAuctions(player, filter);
	}
}
