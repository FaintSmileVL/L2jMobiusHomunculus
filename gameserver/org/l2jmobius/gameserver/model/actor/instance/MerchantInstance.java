package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.data.xml.BuyListData;
import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.enums.TaxType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.model.buylist.ProductList;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.BuyList;
import org.l2jmobius.gameserver.network.serverpackets.ExBuySellList;

/**
 * @version $Revision: 1.10.4.9 $ $Date: 2005/04/11 10:06:08 $
 */
public class MerchantInstance extends NpcInstance
{
	public MerchantInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.MerchantInstance);
	}
	
	@Override
	public boolean isAutoAttackable(Creature attacker)
	{
		if (attacker.isMonster())
		{
			return true;
		}
		return super.isAutoAttackable(attacker);
	}
	
	@Override
	public String getHtmlPath(int npcId, int value, PlayerInstance player)
	{
		String pom;
		if (value == 0)
		{
			pom = Integer.toString(npcId);
		}
		else
		{
			pom = npcId + "-" + value;
		}
		return "data/html/merchant/" + pom + ".htm";
	}
	
	public void showBuyWindow(PlayerInstance player, int value)
	{
		showBuyWindow(player, value, true);
	}
	
	public void showBuyWindow(PlayerInstance player, int value, boolean applyCastleTax)
	{
		final ProductList buyList = BuyListData.getInstance().getBuyList(value);
		if (buyList == null)
		{
			LOGGER.warning("BuyList not found! BuyListId:" + value);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!buyList.isNpcAllowed(getId()))
		{
			LOGGER.warning("Npc not allowed in BuyList! BuyListId:" + value + " NpcId:" + getId());
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		player.setInventoryBlockingStatus(true);
		
		player.sendPacket(new BuyList(buyList, player, (applyCastleTax) ? getCastleTaxRate(TaxType.BUY) : 0));
		player.sendPacket(new ExBuySellList(player, false));
	}
}
