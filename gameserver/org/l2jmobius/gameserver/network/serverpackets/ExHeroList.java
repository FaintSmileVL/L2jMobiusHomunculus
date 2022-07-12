package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Map;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.StatSet;
import org.l2jmobius.gameserver.model.olympiad.Hero;
import org.l2jmobius.gameserver.model.olympiad.Olympiad;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author -Wooden-, KenM, godson
 */
public class ExHeroList implements IClientOutgoingPacket
{
	private final Map<Integer, StatSet> _heroList;
	
	public ExHeroList()
	{
		_heroList = Hero.getInstance().getHeroes();
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_HERO_LIST.writeId(packet);
		
		packet.writeD(_heroList.size());
		for (StatSet hero : _heroList.values())
		{
			packet.writeS(hero.getString(Olympiad.CHAR_NAME));
			packet.writeD(hero.getInt(Olympiad.CLASS_ID));
			packet.writeS(hero.getString(Hero.CLAN_NAME, ""));
			packet.writeD(0x00); // hero.getInt(Hero.CLAN_CREST, 0)
			packet.writeS(hero.getString(Hero.ALLY_NAME, ""));
			packet.writeD(0x00); // hero.getInt(Hero.ALLY_CREST, 0)
			packet.writeD(hero.getInt(Hero.COUNT));
			packet.writeD(0x00);
			packet.writeC(0x00); // 272
		}
		return true;
	}
}
