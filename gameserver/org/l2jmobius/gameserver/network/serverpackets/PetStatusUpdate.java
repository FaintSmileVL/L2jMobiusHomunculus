package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.Summon;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.model.actor.instance.ServitorInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @version $Revision: 1.5.2.3.2.5 $ $Date: 2005/03/29 23:15:10 $
 */
public class PetStatusUpdate implements IClientOutgoingPacket
{
	private final Summon _summon;
	private int _maxFed;
	private int _curFed;
	
	public PetStatusUpdate(Summon summon)
	{
		_summon = summon;
		if (_summon.isPet())
		{
			final PetInstance pet = (PetInstance) _summon;
			_curFed = pet.getCurrentFed(); // how fed it is
			_maxFed = pet.getMaxFed(); // max fed it can be
		}
		else if (_summon.isServitor())
		{
			final ServitorInstance sum = (ServitorInstance) _summon;
			_curFed = sum.getLifeTimeRemaining();
			_maxFed = sum.getLifeTime();
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.PET_STATUS_UPDATE.writeId(packet);
		
		packet.writeD(_summon.getSummonType());
		packet.writeD(_summon.getObjectId());
		packet.writeD(_summon.getX());
		packet.writeD(_summon.getY());
		packet.writeD(_summon.getZ());
		packet.writeS(_summon.getTitle());
		packet.writeD(_curFed);
		packet.writeD(_maxFed);
		packet.writeD((int) _summon.getCurrentHp());
		packet.writeD(_summon.getMaxHp());
		packet.writeD((int) _summon.getCurrentMp());
		packet.writeD(_summon.getMaxMp());
		packet.writeD(_summon.getLevel());
		packet.writeQ(_summon.getStat().getExp());
		packet.writeQ(_summon.getExpForThisLevel()); // 0% absolute value
		packet.writeQ(_summon.getExpForNextLevel()); // 100% absolute value
		packet.writeD(0x01); // TODO: Find me!
		return true;
	}
}
