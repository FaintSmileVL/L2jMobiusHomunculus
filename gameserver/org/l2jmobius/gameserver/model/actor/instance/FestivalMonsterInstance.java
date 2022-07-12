package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.Party;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.model.sevensigns.SevenSignsFestival;
import org.l2jmobius.gameserver.network.serverpackets.InventoryUpdate;

/**
 * @author : Alice
 * @date : 28.09.2021
 * @time : 14:32
 */
public class FestivalMonsterInstance extends MonsterInstance
{
    protected int _bonusMultiplier = 1;

    /**
     * Creates a festival monster.
     * @param template the festival monster NPC template
     */
    public FestivalMonsterInstance(NpcTemplate template)
    {
        super(template);
        setInstanceType(InstanceType.FestivalMonsterInstance);
    }

    public void setOfferingBonus(int bonusMultiplier)
    {
        _bonusMultiplier = bonusMultiplier;
    }

    /**
     * Return True if the attacker is not another FestivalMonsterInstance.
     */
    @Override
    public boolean isAutoAttackable(Creature attacker)
    {
        return !(attacker instanceof FestivalMonsterInstance);
    }

    /**
     * All mobs in the festival are aggressive, and have high aggro range.
     */
    @Override
    public boolean isAggressive()
    {
        return true;
    }

    /**
     * All mobs in the festival really don't need random animation.
     */
    @Override
    public boolean hasRandomAnimation()
    {
        return false;
    }

    /**
     * Actions:
     * <ul>
     * <li>Check if the killing object is a player, and then find the party they belong to.</li>
     * <li>Add a blood offering item to the leader of the party.</li>
     * <li>Update the party leader's inventory to show the new item addition.</li>
     * </ul>
     */
    @Override
    public void doItemDrop(Creature lastAttacker)
    {
        PlayerInstance killingChar = null;
        if (!lastAttacker.isPlayer())
        {
            return;
        }

        killingChar = (PlayerInstance) lastAttacker;
        final Party associatedParty = killingChar.getParty();
        if (associatedParty == null)
        {
            return;
        }

        final PlayerInstance partyLeader = associatedParty.getLeader();
        final ItemInstance addedOfferings = partyLeader.getInventory().addItem("Sign", SevenSignsFestival.FESTIVAL_OFFERING_ID, _bonusMultiplier, partyLeader, this);
        final InventoryUpdate iu = new InventoryUpdate();
        if (addedOfferings.getCount() != _bonusMultiplier)
        {
            iu.addModifiedItem(addedOfferings);
        }
        else
        {
            iu.addNewItem(addedOfferings);
        }
        partyLeader.sendPacket(iu);

        super.doItemDrop(lastAttacker); // Normal drop
    }
}
