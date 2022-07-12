package org.l2jmobius.gameserver.model.itemauction;

/**
 * @author Forsaiken
 */
public enum ItemAuctionState
{
	CREATED((byte) 0),
	STARTED((byte) 1),
	FINISHED((byte) 2);
	
	private final byte _stateId;
	
	ItemAuctionState(byte stateId)
	{
		_stateId = stateId;
	}
	
	public byte getStateId()
	{
		return _stateId;
	}
	
	public static ItemAuctionState stateForStateId(byte stateId)
	{
		for (ItemAuctionState state : ItemAuctionState.values())
		{
			if (state.getStateId() == stateId)
			{
				return state;
			}
		}
		return null;
	}
}