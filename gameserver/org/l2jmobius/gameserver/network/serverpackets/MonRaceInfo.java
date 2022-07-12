package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class MonRaceInfo implements IClientOutgoingPacket
{
	private final int _unknown1;
	private final int _unknown2;
	private final Npc[] _monsters;
	private final int[][] _speeds;
	
	public MonRaceInfo(int unknown1, int unknown2, Npc[] monsters, int[][] speeds)
	{
		/*
		 * -1 0 to initial the race 0 15322 to start race 13765 -1 in middle of race -1 0 to end the race
		 */
		_unknown1 = unknown1;
		_unknown2 = unknown2;
		_monsters = monsters;
		_speeds = speeds;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.MON_RACE_INFO.writeId(packet);
		
		packet.writeD(_unknown1);
		packet.writeD(_unknown2);
		packet.writeD(0x08);
		
		for (int i = 0; i < 8; i++)
		{
			packet.writeD(_monsters[i].getObjectId()); // npcObjectID
			packet.writeD(_monsters[i].getTemplate().getDisplayId() + 1000000); // npcID
			packet.writeD(14107); // origin X
			packet.writeD(181875 + (58 * (7 - i))); // origin Y
			packet.writeD(-3566); // origin Z
			packet.writeD(12080); // end X
			packet.writeD(181875 + (58 * (7 - i))); // end Y
			packet.writeD(-3566); // end Z
			packet.writeF(_monsters[i].getTemplate().getFCollisionHeight()); // coll. height
			packet.writeF(_monsters[i].getTemplate().getFCollisionRadius()); // coll. radius
			packet.writeD(120); // ?? unknown
			for (int j = 0; j < 20; j++)
			{
				if (_unknown1 == 0)
				{
					packet.writeC(_speeds[i][j]);
				}
				else
				{
					packet.writeC(0x00);
				}
			}
		}
		return true;
	}
}
