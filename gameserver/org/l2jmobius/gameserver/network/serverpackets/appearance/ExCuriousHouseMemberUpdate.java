package org.l2jmobius.gameserver.network.serverpackets.appearance;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.ceremonyofchaos.CeremonyOfChaosMember;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExCuriousHouseMemberUpdate implements IClientOutgoingPacket
{
	public int _objId;
	public int _maxHp;
	public int _maxCp;
	public int _currentHp;
	public int _currentCp;
	
	public ExCuriousHouseMemberUpdate(CeremonyOfChaosMember member)
	{
		_objId = member.getObjectId();
		final PlayerInstance player = member.getPlayer();
		if (player != null)
		{
			_maxHp = player.getMaxHp();
			_maxCp = player.getMaxCp();
			_currentHp = (int) player.getCurrentHp();
			_currentCp = (int) player.getCurrentCp();
		}
		else
		{
			_maxHp = 0;
			_maxCp = 0;
			_currentHp = 0;
			_currentCp = 0;
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_CURIOUS_HOUSE_MEMBER_UPDATE.writeId(packet);
		
		packet.writeD(_objId);
		packet.writeD(_maxHp);
		packet.writeD(_maxCp);
		packet.writeD(_currentHp);
		packet.writeD(_currentCp);
		return true;
	}
}
