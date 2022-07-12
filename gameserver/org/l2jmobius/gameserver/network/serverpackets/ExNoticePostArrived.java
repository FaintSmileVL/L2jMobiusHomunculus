package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Migi
 */
public class ExNoticePostArrived implements IClientOutgoingPacket
{
	private static final ExNoticePostArrived STATIC_PACKET_TRUE = new ExNoticePostArrived(true);
	private static final ExNoticePostArrived STATIC_PACKET_FALSE = new ExNoticePostArrived(false);
	
	public static ExNoticePostArrived valueOf(boolean result)
	{
		return result ? STATIC_PACKET_TRUE : STATIC_PACKET_FALSE;
	}
	
	private final boolean _showAnim;
	
	public ExNoticePostArrived(boolean showAnimation)
	{
		_showAnim = showAnimation;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_NOTICE_POST_ARRIVED.writeId(packet);
		
		packet.writeD(_showAnim ? 0x01 : 0x00);
		return true;
	}
}
