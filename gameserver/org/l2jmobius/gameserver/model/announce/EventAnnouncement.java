package org.l2jmobius.gameserver.model.announce;

import java.util.Date;

import org.l2jmobius.gameserver.instancemanager.IdManager;
import org.l2jmobius.gameserver.script.DateRange;

/**
 * @author UnAfraid
 */
public class EventAnnouncement implements IAnnouncement
{
	private final int _id;
	private final DateRange _range;
	private String _content;
	
	public EventAnnouncement(DateRange range, String content)
	{
		_id = IdManager.getInstance().getNextId();
		_range = range;
		_content = content;
	}
	
	@Override
	public int getId()
	{
		return _id;
	}
	
	@Override
	public AnnouncementType getType()
	{
		return AnnouncementType.EVENT;
	}
	
	@Override
	public void setType(AnnouncementType type)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isValid()
	{
		return _range.isWithinRange(new Date());
	}
	
	@Override
	public String getContent()
	{
		return _content;
	}
	
	@Override
	public void setContent(String content)
	{
		_content = content;
	}
	
	@Override
	public String getAuthor()
	{
		return "N/A";
	}
	
	@Override
	public void setAuthor(String author)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean deleteMe()
	{
		IdManager.getInstance().releaseId(_id);
		return true;
	}
	
	@Override
	public boolean storeMe()
	{
		return true;
	}
	
	@Override
	public boolean updateMe()
	{
		throw new UnsupportedOperationException();
	}
}
