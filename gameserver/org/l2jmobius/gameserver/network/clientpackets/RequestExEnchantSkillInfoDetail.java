package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import org.l2jmobius.gameserver.enums.SkillEnchantType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.serverpackets.ExEnchantSkillInfoDetail;

/**
 * @author -Wooden-
 */
public class RequestExEnchantSkillInfoDetail implements IClientIncomingPacket
{
	private SkillEnchantType _type;
	private int _skillId;
	private int _skillLevel;
	private int _skillSubLevel;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_type = SkillEnchantType.values()[packet.readD()];
		_skillId = packet.readD();
		_skillLevel = packet.readH();
		_skillSubLevel = packet.readH();
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		if ((_skillId <= 0) || (_skillLevel <= 0) || (_skillSubLevel < 0))
		{
			return;
		}
		
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		player.sendPacket(new ExEnchantSkillInfoDetail(_type, _skillId, _skillLevel, _skillSubLevel, player));
	}
}
