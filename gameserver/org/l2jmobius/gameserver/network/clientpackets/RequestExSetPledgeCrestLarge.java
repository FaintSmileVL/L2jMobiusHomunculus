package org.l2jmobius.gameserver.network.clientpackets;

import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.data.sql.CrestTable;
import org.l2jmobius.gameserver.model.Crest;
import org.l2jmobius.gameserver.model.Crest.CrestType;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.clan.Clan;
import org.l2jmobius.gameserver.model.clan.ClanPrivilege;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.SystemMessageId;

/**
 * Format : chdb c (id) 0xD0 h (subid) 0x11 d data size b raw data (picture i think ;) )
 * @author -Wooden-
 */
public class RequestExSetPledgeCrestLarge implements IClientIncomingPacket
{
	private int _length;
	private byte[] _data = null;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_length = packet.readD();
		if (_length > 2176)
		{
			return false;
		}
		
		_data = packet.readB(_length);
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
		
		final Clan clan = player.getClan();
		if (clan == null)
		{
			return;
		}
		
		if ((_length < 0) || (_length > 2176))
		{
			client.sendPacket(SystemMessageId.THE_SIZE_OF_THE_UPLOADED_SYMBOL_DOES_NOT_MEET_THE_STANDARD_REQUIREMENTS);
			return;
		}
		
		if (clan.getDissolvingExpiryTime() > Chronos.currentTimeMillis())
		{
			client.sendPacket(SystemMessageId.AS_YOU_ARE_CURRENTLY_SCHEDULE_FOR_CLAN_DISSOLUTION_YOU_CANNOT_REGISTER_OR_DELETE_A_CLAN_CREST);
			return;
		}
		
		if (!player.hasClanPrivilege(ClanPrivilege.CL_REGISTER_CREST))
		{
			client.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		
		if (_length == 0)
		{
			if (clan.getCrestLargeId() != 0)
			{
				clan.changeLargeCrest(0);
				client.sendPacket(SystemMessageId.THE_CLAN_MARK_HAS_BEEN_DELETED);
			}
		}
		else
		{
			if (clan.getLevel() < 3)
			{
				client.sendPacket(SystemMessageId.THE_CLAN_MUST_BE_LEVEL_3_OR_ABOVE_TO_REGISTER_A_CLAN_CREST);
				return;
			}
			
			final Crest crest = CrestTable.getInstance().createCrest(_data, CrestType.PLEDGE_LARGE);
			if (crest != null)
			{
				clan.changeLargeCrest(crest.getId());
				client.sendPacket(SystemMessageId.THE_CLAN_MARK_WAS_SUCCESSFULLY_REGISTERED_THE_SYMBOL_WILL_APPEAR_ON_THE_CLAN_FLAG_AND_THE_INSIGNIA_IS_ONLY_DISPLAYED_ON_ITEMS_PERTAINING_TO_A_CLAN_THAT_OWNS_A_CLAN_HALL_OR_CASTLE);
			}
		}
	}
}