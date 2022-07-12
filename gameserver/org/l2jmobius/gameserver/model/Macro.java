package org.l2jmobius.gameserver.model;

import java.util.List;

import org.l2jmobius.gameserver.model.interfaces.IIdentifiable;
import org.l2jmobius.gameserver.model.interfaces.INamable;

public class Macro implements IIdentifiable, INamable
{
	private int _id;
	private final int _icon;
	private final String _name;
	private final String _descr;
	private final String _acronym;
	private final List<MacroCmd> _commands;
	
	/**
	 * Constructor for macros.
	 * @param id the macro ID
	 * @param icon the icon ID
	 * @param name the macro name
	 * @param descr the macro description
	 * @param acronym the macro acronym
	 * @param list the macro command list
	 */
	public Macro(int id, int icon, String name, String descr, String acronym, List<MacroCmd> list)
	{
		_id = id;
		_icon = icon;
		_name = name;
		_descr = descr;
		_acronym = acronym;
		_commands = list;
	}
	
	/**
	 * Gets the marco ID.
	 * @returns the marco ID
	 */
	@Override
	public int getId()
	{
		return _id;
	}
	
	/**
	 * Sets the marco ID.
	 * @param id the marco ID
	 */
	public void setId(int id)
	{
		_id = id;
	}
	
	/**
	 * Gets the macro icon ID.
	 * @return the icon
	 */
	public int getIcon()
	{
		return _icon;
	}
	
	/**
	 * Gets the macro name.
	 * @return the name
	 */
	@Override
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Gets the macro description.
	 * @return the description
	 */
	public String getDescr()
	{
		return _descr;
	}
	
	/**
	 * Gets the macro acronym.
	 * @return the acronym
	 */
	public String getAcronym()
	{
		return _acronym;
	}
	
	/**
	 * Gets the macro command list.
	 * @return the macro command list
	 */
	public List<MacroCmd> getCommands()
	{
		return _commands;
	}
}
