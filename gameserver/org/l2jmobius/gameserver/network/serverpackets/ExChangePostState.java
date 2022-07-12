package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Migi
 */
public class ExChangePostState implements IClientOutgoingPacket
{
	private final boolean _receivedBoard;
	private final int[] _changedMsgIds;
	private final int _changeId;
	
	public ExChangePostState(boolean receivedBoard, int[] changedMsgIds, int changeId)
	{
		_receivedBoard = receivedBoard;
		_changedMsgIds = changedMsgIds;
		_changeId = changeId;
	}
	
	public ExChangePostState(boolean receivedBoard, int changedMsgId, int changeId)
	{
		_receivedBoard = receivedBoard;
		_changedMsgIds = new int[]
		{
			changedMsgId
		};
		_changeId = changeId;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CHANGE_POST_STATE.writeId(packet);
		
		packet.writeD(_receivedBoard ? 1 : 0);
		packet.writeD(_changedMsgIds.length);
		for (int postId : _changedMsgIds)
		{
			packet.writeD(postId); // postId
			packet.writeD(_changeId); // state
		}
		return true;
	}
}
