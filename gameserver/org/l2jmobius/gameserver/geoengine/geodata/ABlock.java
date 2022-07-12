package org.l2jmobius.gameserver.geoengine.geodata;

public abstract class ABlock
{
	/**
	 * Checks the block for having geodata.
	 * @return boolean : True, when block has geodata (Flat, Complex, Multilayer).
	 */
	public abstract boolean hasGeoPos();
	
	/**
	 * Returns the height of cell, which is closest to given coordinates.<br>
	 * @param geoX : Cell geodata X coordinate.
	 * @param geoY : Cell geodata Y coordinate.
	 * @param worldZ : Cell world Z coordinate.
	 * @return short : Cell geodata Z coordinate, nearest to given coordinates.
	 */
	public abstract short getHeightNearest(int geoX, int geoY, int worldZ);
	
	/**
	 * Returns the NSWE flag byte of cell, which is closest to given coordinates.<br>
	 * @param geoX : Cell geodata X coordinate.
	 * @param geoY : Cell geodata Y coordinate.
	 * @param worldZ : Cell world Z coordinate.
	 * @return short : Cell NSWE flag byte, nearest to given coordinates.
	 */
	public abstract byte getNsweNearest(int geoX, int geoY, int worldZ);
	
	/**
	 * Returns index to data of the cell, which is closes layer to given coordinates.<br>
	 * @param geoX : Cell geodata X coordinate.
	 * @param geoY : Cell geodata Y coordinate.
	 * @param worldZ : Cell world Z coordinate.
	 * @return {@code int} : Cell index.
	 */
	public abstract int getIndexNearest(int geoX, int geoY, int worldZ);
	
	/**
	 * Returns index to data of the cell, which is first layer above given coordinates.<br>
	 * @param geoX : Cell geodata X coordinate.
	 * @param geoY : Cell geodata Y coordinate.
	 * @param worldZ : Cell world Z coordinate.
	 * @return {@code int} : Cell index. -1..when no layer available below given Z coordinate.
	 */
	public abstract int getIndexAbove(int geoX, int geoY, int worldZ);
	
	/**
	 * Returns index to data of the cell, which is first layer below given coordinates.<br>
	 * @param geoX : Cell geodata X coordinate.
	 * @param geoY : Cell geodata Y coordinate.
	 * @param worldZ : Cell world Z coordinate.
	 * @return {@code int} : Cell index. -1..when no layer available below given Z coordinate.
	 */
	public abstract int getIndexBelow(int geoX, int geoY, int worldZ);
	
	/**
	 * Returns the height of cell given by cell index.<br>
	 * @param index : Index of the cell.
	 * @return short : Cell geodata Z coordinate, below given coordinates.
	 */
	public abstract short getHeight(int index);
	
	/**
	 * Returns the NSWE flag byte of cell given by cell index.<br>
	 * @param index : Index of the cell.
	 * @return short : Cell geodata Z coordinate, below given coordinates.
	 */
	public abstract byte getNswe(int index);
}