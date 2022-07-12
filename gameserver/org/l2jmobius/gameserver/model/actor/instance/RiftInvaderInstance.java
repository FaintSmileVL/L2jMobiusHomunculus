package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author : Alice
 * @date : 28.09.2021
 * @time : 15:00
 */
public class RiftInvaderInstance extends MonsterInstance
{
    // Not longer needed since rift monster targeting control now is handled by the room zones for any mob
    public RiftInvaderInstance(NpcTemplate template)
    {
        super(template);
        setInstanceType(InstanceType.RiftInvaderInstance);
    }
}