package org.l2jmobius.gameserver.model;

import util.Rnd;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Alice
 * @date : 28.09.2021
 * @time : 14:48
 */
public class DimensionalRiftRoom {
    private final byte _type;
    private final byte _room;
    private final int _xMin;
    private final int _xMax;
    private final int _yMin;
    private final int _yMax;
    private final int _zMin;
    private final int _zMax;
    private final Location _teleportCoords;
    private final Shape _s;
    private final boolean _isBossRoom;
    private final java.util.List<Spawn> _roomSpawns = new ArrayList<>();
    private boolean _partyInside = false;

    public DimensionalRiftRoom(byte type, byte room, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, int xT, int yT, int zT, boolean isBossRoom)
    {
        _type = type;
        _room = room;
        _xMin = (xMin + 128);
        _xMax = (xMax - 128);
        _yMin = (yMin + 128);
        _yMax = (yMax - 128);
        _zMin = zMin;
        _zMax = zMax;
        _teleportCoords = new Location(xT, yT, zT);
        _isBossRoom = isBossRoom;
        _s = new Polygon(new int[]
                {
                        xMin,
                        xMax,
                        xMax,
                        xMin
                }, new int[]
                {
                        yMin,
                        yMin,
                        yMax,
                        yMax
                }, 4);
    }

    public byte getType()
    {
        return _type;
    }

    public byte getRoom()
    {
        return _room;
    }

    public int getRandomX()
    {
        return Rnd.get(_xMin, _xMax);
    }

    public int getRandomY()
    {
        return Rnd.get(_yMin, _yMax);
    }

    public Location getTeleportCoorinates()
    {
        return _teleportCoords;
    }

    public boolean checkIfInZone(int x, int y, int z)
    {
        return _s.contains(x, y) && (z >= _zMin) && (z <= _zMax);
    }

    public boolean isBossRoom()
    {
        return _isBossRoom;
    }

    public List<Spawn> getSpawns()
    {
        return _roomSpawns;
    }

    public void spawn()
    {
        for (Spawn spawn : _roomSpawns)
        {
            spawn.doSpawn();
            spawn.startRespawn();
        }
    }

    public DimensionalRiftRoom unspawn()
    {
        for (Spawn spawn : _roomSpawns)
        {
            spawn.stopRespawn();
            if (spawn.getLastSpawn() != null)
            {
                spawn.getLastSpawn().deleteMe();
            }
        }
        return this;
    }

    /**
     * Returns if party is inside the room.
     * @return {@code true} if there is a party inside, {@code false} otherwise
     */
    public boolean isPartyInside()
    {
        return _partyInside;
    }

    /**
     * Sets the party inside.
     * @param partyInside
     */
    public void setPartyInside(boolean partyInside)
    {
        _partyInside = partyInside;
    }
}
