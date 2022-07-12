package org.l2jmobius.gameserver.model;

/**
 * @author malyelfik
 */
public class CropProcure extends SeedProduction
{
	private final int _rewardType;
	
	public CropProcure(int id, long amount, int type, long startAmount, long price)
	{
		super(id, amount, price, startAmount);
		_rewardType = type;
	}
	
	public int getReward()
	{
		return _rewardType;
	}
}