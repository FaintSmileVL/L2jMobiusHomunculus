package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author NightMarez
 * @version $Revision: 1.3.2.2.2.5 $ $Date: 2005/03/27 15:29:32 $
 */
public class ObservationInstance extends Npc
{
	public ObservationInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.ObservationInstance);
	}
	
	@Override
	public void showChatWindow(PlayerInstance player, int value)
	{
		String filename = null;
		if (isInsideRadius2D(-79884, 86529, 0, 50) || isInsideRadius2D(-78858, 111358, 0, 50) || isInsideRadius2D(-76973, 87136, 0, 50) || isInsideRadius2D(-75850, 111968, 0, 50))
		{
			if (value == 0)
			{
				filename = "data/html/observation/" + getId() + "-Oracle.htm";
			}
			else
			{
				filename = "data/html/observation/" + getId() + "-Oracle-" + value + ".htm";
			}
		}
		else if (value == 0)
		{
			filename = "data/html/observation/" + getId() + ".htm";
		}
		else
		{
			filename = "data/html/observation/" + getId() + "-" + value + ".htm";
		}
		
		final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile(player, filename);
		html.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(html);
	}
}