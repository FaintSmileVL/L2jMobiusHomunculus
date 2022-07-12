package org.l2jmobius.gameserver.network.serverpackets.commission;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.commission.CommissionItem;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author NosBit
 */
public class ExResponseCommissionBuyInfo extends AbstractItemPacket
{
	public static final ExResponseCommissionBuyInfo FAILED = new ExResponseCommissionBuyInfo(null);
	
	private final CommissionItem _commissionItem;
	
	public ExResponseCommissionBuyInfo(CommissionItem commissionItem)
	{
		_commissionItem = commissionItem;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_RESPONSE_COMMISSION_BUY_INFO.writeId(packet);
		
		packet.writeD(_commissionItem != null ? 1 : 0);
		if (_commissionItem != null)
		{
			packet.writeQ(_commissionItem.getPricePerUnit());
			packet.writeQ(_commissionItem.getCommissionId());
			packet.writeD(0); // CommissionItemType seems client does not really need it.
			writeItem(packet, _commissionItem.getItemInfo());
		}
		return true;
	}
}
