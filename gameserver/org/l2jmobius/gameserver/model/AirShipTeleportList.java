package org.l2jmobius.gameserver.model;

/**
 * Holds a list of all AirShip teleports.
 * @author xban1x
 */
public class AirShipTeleportList
{
	private final int _location;
	private final int[] _fuel;
	private final VehiclePathPoint[][] _routes;
	
	public AirShipTeleportList(int loc, int[] f, VehiclePathPoint[][] r)
	{
		_location = loc;
		_fuel = f;
		_routes = r;
	}
	
	public int getLocation()
	{
		return _location;
	}
	
	public int[] getFuel()
	{
		return _fuel;
	}
	
	public VehiclePathPoint[][] getRoute()
	{
		return _routes;
	}
}
