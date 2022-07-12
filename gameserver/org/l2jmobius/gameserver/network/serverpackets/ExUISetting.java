package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Mobius
 */
public class ExUISetting implements IClientOutgoingPacket
{
	public static final String UI_KEY_MAPPING_VAR = "UI_KEY_MAPPING";
	public static final String SPLIT_VAR = "	";
	private final byte[] _uiKeyMapping;
	
	public ExUISetting(PlayerInstance player)
	{
		if (player.getVariables().hasVariable(UI_KEY_MAPPING_VAR))
		{
			_uiKeyMapping = player.getVariables().getByteArray(UI_KEY_MAPPING_VAR, SPLIT_VAR);
		}
		else
		{
			_uiKeyMapping = null;
		}
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_UI_SETTING.writeId(packet);
		if (_uiKeyMapping != null)
		{
			packet.writeD(_uiKeyMapping.length);
			packet.writeB(_uiKeyMapping);
		}
		else
		{
			packet.writeD(0);
		}
		return true;
	}
}
