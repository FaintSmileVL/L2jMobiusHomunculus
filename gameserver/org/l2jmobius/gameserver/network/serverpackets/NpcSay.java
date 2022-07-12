package org.l2jmobius.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import network.PacketWriter;
import org.l2jmobius.gameserver.enums.ChatType;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.network.NpcStringId;
import org.l2jmobius.gameserver.network.NpcStringId.NSLocalisation;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author Kerberos
 */
public class NpcSay implements IClientOutgoingPacket
{
	private final int _objectId;
	private final ChatType _textType;
	private final int _npcId;
	private String _text;
	private final int _npcString;
	private List<String> _parameters;
	private String _lang;
	
	/**
	 * @param objectId
	 * @param messageType
	 * @param npcId
	 * @param text
	 */
	public NpcSay(int objectId, ChatType messageType, int npcId, String text)
	{
		_objectId = objectId;
		_textType = messageType;
		_npcId = 1000000 + npcId;
		_npcString = -1;
		_text = text;
	}
	
	public NpcSay(Npc npc, ChatType messageType, String text)
	{
		_objectId = npc.getObjectId();
		_textType = messageType;
		_npcId = 1000000 + npc.getTemplate().getDisplayId();
		_npcString = -1;
		_text = text;
	}
	
	public NpcSay(int objectId, ChatType messageType, int npcId, NpcStringId npcString)
	{
		_objectId = objectId;
		_textType = messageType;
		_npcId = 1000000 + npcId;
		_npcString = npcString.getId();
	}
	
	public NpcSay(Npc npc, ChatType messageType, NpcStringId npcString)
	{
		_objectId = npc.getObjectId();
		_textType = messageType;
		_npcId = 1000000 + npc.getTemplate().getDisplayId();
		_npcString = npcString.getId();
	}
	
	/**
	 * @param text the text to add as a parameter for this packet's message (replaces S1, S2 etc.)
	 * @return this NpcSay packet object
	 */
	public NpcSay addStringParameter(String text)
	{
		if (_parameters == null)
		{
			_parameters = new ArrayList<>();
		}
		_parameters.add(text);
		return this;
	}
	
	/**
	 * @param params a list of strings to add as parameters for this packet's message (replaces S1, S2 etc.)
	 * @return this NpcSay packet object
	 */
	public NpcSay addStringParameters(String... params)
	{
		if ((params != null) && (params.length > 0))
		{
			if (_parameters == null)
			{
				_parameters = new ArrayList<>();
			}
			
			for (String item : params)
			{
				if ((item != null) && (item.length() > 0))
				{
					_parameters.add(item);
				}
			}
		}
		return this;
	}
	
	public void setLang(String lang)
	{
		_lang = lang;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.NPC_SAY.writeId(packet);
		
		packet.writeD(_objectId);
		packet.writeD(_textType.getClientId());
		packet.writeD(_npcId);
		
		// Localisation related.
		if (_lang != null)
		{
			final NpcStringId ns = NpcStringId.getNpcStringId(_npcString);
			if (ns != null)
			{
				final NSLocalisation nsl = ns.getLocalisation(_lang);
				if (nsl != null)
				{
					packet.writeD(-1);
					packet.writeS(nsl.getLocalisation(_parameters != null ? _parameters : Collections.emptyList()));
					return true;
				}
			}
		}
		
		packet.writeD(_npcString);
		if (_npcString == -1)
		{
			packet.writeS(_text);
		}
		else if (_parameters != null)
		{
			for (String s : _parameters)
			{
				packet.writeS(s);
			}
		}
		return true;
	}
}