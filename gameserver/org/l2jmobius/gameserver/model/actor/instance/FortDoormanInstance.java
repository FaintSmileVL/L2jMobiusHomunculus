package org.l2jmobius.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;

public class FortDoormanInstance extends DoormanInstance
{
	public FortDoormanInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.FortDoormanInstance);
	}
	
	@Override
	public void showChatWindow(PlayerInstance player)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
		
		final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		if (!isOwnerClan(player))
		{
			html.setFile(player, "data/html/doorman/" + getTemplate().getId() + "-no.htm");
		}
		else if (isUnderSiege())
		{
			html.setFile(player, "data/html/doorman/" + getTemplate().getId() + "-busy.htm");
		}
		else
		{
			html.setFile(player, "data/html/doorman/" + getTemplate().getId() + ".htm");
		}
		
		html.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(html);
	}
	
	@Override
	protected final void openDoors(PlayerInstance player, String command)
	{
		final StringTokenizer st = new StringTokenizer(command.substring(10), ", ");
		st.nextToken();
		
		while (st.hasMoreTokens())
		{
			getFort().openDoor(player, Integer.parseInt(st.nextToken()));
		}
	}
	
	@Override
	protected final void closeDoors(PlayerInstance player, String command)
	{
		final StringTokenizer st = new StringTokenizer(command.substring(11), ", ");
		st.nextToken();
		
		while (st.hasMoreTokens())
		{
			getFort().closeDoor(player, Integer.parseInt(st.nextToken()));
		}
	}
	
	@Override
	protected final boolean isOwnerClan(PlayerInstance player)
	{
		return (player.getClan() != null) && (getFort() != null) && (getFort().getOwnerClan() != null) && (player.getClanId() == getFort().getOwnerClan().getId());
	}
	
	@Override
	protected final boolean isUnderSiege()
	{
		return getFort().getZone().isActive();
	}
}