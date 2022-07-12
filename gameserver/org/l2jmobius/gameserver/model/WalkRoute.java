package org.l2jmobius.gameserver.model;

import java.util.List;

/**
 * @author GKR
 */
public class WalkRoute
{
	private final String _name;
	private final List<NpcWalkerNode> _nodeList; // List of nodes
	private final boolean _repeatWalk; // Does repeat walk, after arriving into last point in list, or not
	private boolean _stopAfterCycle; // Make only one cycle or endlessly
	private final byte _repeatType; // Repeat style: 0 - go back, 1 - go to first point (circle style), 2 - teleport to first point (conveyor style), 3 - random walking between points
	
	public WalkRoute(String name, List<NpcWalkerNode> route, boolean repeat, byte repeatType)
	{
		_name = name;
		_nodeList = route;
		_repeatType = repeatType;
		_repeatWalk = (_repeatType >= 0) && (_repeatType <= 2) && repeat;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public List<NpcWalkerNode> getNodeList()
	{
		return _nodeList;
	}
	
	public NpcWalkerNode getLastNode()
	{
		return _nodeList.get(_nodeList.size() - 1);
	}
	
	public boolean repeatWalk()
	{
		return _repeatWalk;
	}
	
	public boolean doOnce()
	{
		return _stopAfterCycle;
	}
	
	public byte getRepeatType()
	{
		return _repeatType;
	}
	
	public int getNodesCount()
	{
		return _nodeList.size();
	}
}
