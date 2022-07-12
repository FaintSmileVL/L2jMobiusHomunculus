package org.l2jmobius.gameserver.model.options;

import java.util.logging.Logger;

/**
 * @author Pere, Mobius
 */
public class Variation
{
	private static final Logger LOGGER = Logger.getLogger(Variation.class.getSimpleName());
	
	private final int _mineralId;
	private final OptionDataGroup[] _effects = new OptionDataGroup[2];
	
	public Variation(int mineralId)
	{
		_mineralId = mineralId;
	}
	
	public int getMineralId()
	{
		return _mineralId;
	}
	
	public void setEffectGroup(int order, OptionDataGroup group)
	{
		_effects[order] = group;
	}
	
	public Options getRandomEffect(int order, int targetItemId)
	{
		if ((_effects == null) || (_effects[order] == null))
		{
			LOGGER.warning("Null effect: for mineral " + _mineralId + ", order " + order);
			return null;
		}
		return _effects[order].getRandomEffect(targetItemId);
	}
}