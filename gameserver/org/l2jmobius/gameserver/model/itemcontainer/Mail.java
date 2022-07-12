package org.l2jmobius.gameserver.model.itemcontainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.enums.ItemLocation;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;

/**
 * @author DS
 */
public class Mail extends ItemContainer
{
	private final int _ownerId;
	private int _messageId;
	
	public Mail(int objectId, int messageId)
	{
		_ownerId = objectId;
		_messageId = messageId;
	}
	
	@Override
	public String getName()
	{
		return "Mail";
	}
	
	@Override
	public PlayerInstance getOwner()
	{
		return null;
	}
	
	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.MAIL;
	}
	
	public int getMessageId()
	{
		return _messageId;
	}
	
	public void setNewMessageId(int messageId)
	{
		_messageId = messageId;
		for (ItemInstance item : _items.values())
		{
			item.setItemLocation(getBaseLocation(), messageId);
		}
		updateDatabase();
	}
	
	public void returnToWh(ItemContainer wh)
	{
		for (ItemInstance item : _items.values())
		{
			if (wh == null)
			{
				item.setItemLocation(ItemLocation.WAREHOUSE);
			}
			else
			{
				transferItem("Expire", item.getObjectId(), item.getCount(), wh, null, null);
			}
		}
	}
	
	@Override
	protected void addItem(ItemInstance item)
	{
		super.addItem(item);
		item.setItemLocation(getBaseLocation(), _messageId);
		item.updateDatabase(true);
	}
	
	/*
	 * Allow saving of the items without owner
	 */
	@Override
	public void updateDatabase()
	{
		for (ItemInstance item : _items.values())
		{
			item.updateDatabase(true);
		}
	}
	
	@Override
	public void restore()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM items WHERE owner_id=? AND loc=? AND loc_data=?"))
		{
			statement.setInt(1, _ownerId);
			statement.setString(2, getBaseLocation().name());
			statement.setInt(3, _messageId);
			try (ResultSet inv = statement.executeQuery())
			{
				while (inv.next())
				{
					final ItemInstance item = new ItemInstance(inv);
					World.getInstance().addObject(item);
					
					// If stackable item is found just add to current quantity
					if (item.isStackable() && (getItemByItemId(item.getId()) != null))
					{
						addItem("Restore", item, null, null);
					}
					else
					{
						addItem(item);
					}
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "could not restore container:", e);
		}
	}
	
	@Override
	public void deleteMe()
	{
		for (ItemInstance item : _items.values())
		{
			item.updateDatabase(true);
			item.stopAllTasks();
			World.getInstance().removeObject(item);
		}
		
		_items.clear();
	}
	
	@Override
	public int getOwnerId()
	{
		return _ownerId;
	}
}