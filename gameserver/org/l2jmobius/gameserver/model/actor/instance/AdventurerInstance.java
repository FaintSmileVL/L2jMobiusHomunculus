package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author : Alice
 * @date : 27.09.2021
 * @time : 21:38
 */
public class AdventurerInstance extends NpcInstance {
    public AdventurerInstance(NpcTemplate template) {
        super(template);
        setInstanceType(InstanceType.AdventurerInstance);
    }

    @Override
    public String getHtmlPath(int npcId, int value, PlayerInstance player) {
        String pom = "";
        if (value == 0) {
            pom = Integer.toString(npcId);
        } else {
            pom = npcId + "-" + value;
        }
        return "data/html/adventurer_guildsman/" + pom + ".htm";
    }
}