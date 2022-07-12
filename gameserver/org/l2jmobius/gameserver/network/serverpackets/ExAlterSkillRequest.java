package org.l2jmobius.gameserver.network.serverpackets;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.ThreadPool;
import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author UnAfraid, Mobius
 */
public class ExAlterSkillRequest implements IClientOutgoingPacket
{
	private final int _currentSkillId;
	private final int _nextSkillId;
	private final int _alterTime;
	private final PlayerInstance _player;
	
	public ExAlterSkillRequest(PlayerInstance player, int currentSkill, int nextSkill, int alterTime)
	{
		_player = player;
		_currentSkillId = currentSkill;
		_nextSkillId = nextSkill;
		_alterTime = alterTime;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		if (!Config.ENABLE_ALTER_SKILLS)
		{
			return true;
		}
		
		OutgoingPackets.EX_ALTER_SKILL_REQUEST.writeId(packet);
		packet.writeD(_nextSkillId);
		packet.writeD(_currentSkillId);
		packet.writeD(_alterTime);
		
		if (_alterTime > 0)
		{
			_player.setAlterSkillActive(true);
			ThreadPool.schedule(() ->
			{
				_player.sendPacket(new ExAlterSkillRequest(null, -1, -1, -1));
				_player.setAlterSkillActive(false);
			}, _alterTime * 1000);
		}
		
		return true;
	}
}
