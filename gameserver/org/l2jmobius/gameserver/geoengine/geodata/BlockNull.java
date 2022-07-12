package org.l2jmobius.gameserver.geoengine.geodata;

public class BlockNull extends ABlock
{
	@Override
	public boolean hasGeoPos()
	{
		return false;
	}
	
	@Override
	public short getHeightNearest(int geoX, int geoY, int worldZ)
	{
		return (short) worldZ;
	}
	
	@Override
	public byte getNsweNearest(int geoX, int geoY, int worldZ)
	{
		return GeoStructure.CELL_FLAG_ALL;
	}
	
	@Override
	public int getIndexNearest(int geoX, int geoY, int worldZ)
	{
		return 0;
	}
	
	@Override
	public int getIndexAbove(int geoX, int geoY, int worldZ)
	{
		return 0;
	}
	
	@Override
	public int getIndexBelow(int geoX, int geoY, int worldZ)
	{
		return 0;
	}
	
	@Override
	public short getHeight(int index)
	{
		return 0;
	}
	
	@Override
	public byte getNswe(int index)
	{
		return GeoStructure.CELL_FLAG_ALL;
	}
}