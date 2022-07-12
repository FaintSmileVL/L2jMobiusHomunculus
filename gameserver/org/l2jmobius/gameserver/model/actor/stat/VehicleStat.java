package org.l2jmobius.gameserver.model.actor.stat;

import org.l2jmobius.gameserver.model.actor.Vehicle;

public class VehicleStat extends CreatureStat
{
	private float _moveSpeed = 0;
	private int _rotationSpeed = 0;
	
	public VehicleStat(Vehicle activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public double getMoveSpeed()
	{
		return _moveSpeed;
	}
	
	public void setMoveSpeed(float speed)
	{
		_moveSpeed = speed;
	}
	
	public double getRotationSpeed()
	{
		return _rotationSpeed;
	}
	
	public void setRotationSpeed(int speed)
	{
		_rotationSpeed = speed;
	}
}