package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.items.instance.ItemInstance;
import org.l2jmobius.gameserver.network.OutgoingPackets;

public class EnchantResult implements IClientOutgoingPacket
{
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int ERROR = 2;
	public static final int BLESSED_FAIL = 3;
	public static final int NO_CRYSTAL = 4;
	public static final int SAFE_FAIL = 5;
	
	private final int _result;
	private final int _crystal;
	private final int _count;
	private final int _enchantLevel;
	@SuppressWarnings("unused")
	private final int[] _enchantOptions;
	
	public EnchantResult(int result, int crystal, int count, int enchantLevel, int[] options)
	{
		_result = result;
		_crystal = crystal;
		_count = count;
		_enchantLevel = enchantLevel;
		_enchantOptions = options;
	}
	
	public EnchantResult(int result, int crystal, int count)
	{
		this(result, crystal, count, 0, ItemInstance.DEFAULT_ENCHANT_OPTIONS);
	}
	
	public EnchantResult(int result, ItemInstance item)
	{
		this(result, 0, 0, item.getEnchantLevel(), item.getEnchantOptions());
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.ENCHANT_RESULT.writeId(packet);
		
		packet.writeD(_result);
		packet.writeD(_crystal);
		packet.writeQ(_count);
		
		// Guessing.
		// With 166 options became 3x write integers instead of shorts and enchant level moved bellow.
		
		// Commenting until actually knowing.
		// for (int option : _enchantOptions)
		// {
		// packet.writeD(option);
		// }
		packet.writeD(0x00);
		packet.writeD(0x00);
		packet.writeD(0x00);
		
		packet.writeD(_enchantLevel); // Confirmed.
		
		return true;
	}
}
