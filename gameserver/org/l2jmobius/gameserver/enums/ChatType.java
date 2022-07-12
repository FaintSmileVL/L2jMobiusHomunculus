package org.l2jmobius.gameserver.enums;

/**
 * @author St3eT
 */
public enum ChatType
{
	GENERAL(0),
	SHOUT(1),
	WHISPER(2),
	PARTY(3),
	CLAN(4),
	GM(5),
	PETITION_PLAYER(6),
	PETITION_GM(7),
	TRADE(8),
	ALLIANCE(9),
	ANNOUNCEMENT(10),
	BOAT(11),
	FRIEND(12),
	MSNCHAT(13),
	PARTYMATCH_ROOM(14),
	PARTYROOM_COMMANDER(15),
	PARTYROOM_ALL(16),
	HERO_VOICE(17),
	CRITICAL_ANNOUNCE(18),
	SCREEN_ANNOUNCE(19),
	BATTLEFIELD(20),
	MPCC_ROOM(21),
	NPC_GENERAL(22),
	NPC_SHOUT(23),
	NPC_WHISPER(24),
	WORLD(25);
	
	private final int _clientId;
	
	private ChatType(int clientId)
	{
		_clientId = clientId;
	}
	
	/**
	 * @return the client id.
	 */
	public int getClientId()
	{
		return _clientId;
	}
	
	/**
	 * Finds the {@code ChatType} by its clientId
	 * @param clientId the clientId
	 * @return the {@code ChatType} if its found, {@code null} otherwise.
	 */
	public static ChatType findByClientId(int clientId)
	{
		for (ChatType ChatType : values())
		{
			if (ChatType.getClientId() == clientId)
			{
				return ChatType;
			}
		}
		return null;
	}
}