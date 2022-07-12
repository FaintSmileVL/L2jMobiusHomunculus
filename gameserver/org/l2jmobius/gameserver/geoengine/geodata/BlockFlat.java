package org.l2jmobius.gameserver.geoengine.geodata;

import java.nio.ByteBuffer;

import org.l2jmobius.gameserver.enums.GeoType;

public class BlockFlat extends ABlock
{
	protected final short _height;
	protected byte _nswe;
	
	/**
	 * Creates FlatBlock.
	 * @param bb : Input byte buffer.
	 * @param type : The type of loaded geodata.
	 */
	public BlockFlat(ByteBuffer bb, GeoType type)
	{
		// Get height and nswe.
		_height = bb.getShort();
		_nswe = GeoStructure.CELL_FLAG_ALL;
		
		// Read dummy data.
		if (type == GeoType.L2OFF)
		{
			bb.getShort();
		}
	}
	
	@Override
	public boolean hasGeoPos()
	{
		return true;
	}
	
	@Override
	public short getHeightNearest(int geoX, int geoY, int worldZ)
	{
		return _height;
	}
	
	@Override
	public byte getNsweNearest(int geoX, int geoY, int worldZ)
	{
		return _nswe;
	}
	
	@Override
	public int getIndexNearest(int geoX, int geoY, int worldZ)
	{
		return 0;
	}
	
	@Override
	public int getIndexAbove(int geoX, int geoY, int worldZ)
	{
		// Check height and return index.
		return _height > worldZ ? 0 : -1;
	}
	
	@Override
	public int getIndexBelow(int geoX, int geoY, int worldZ)
	{
		// Check height and return index.
		return _height < worldZ ? 0 : -1;
	}
	
	@Override
	public short getHeight(int index)
	{
		return _height;
	}
	
	@Override
	public byte getNswe(int index)
	{
		return _nswe;
	}
}