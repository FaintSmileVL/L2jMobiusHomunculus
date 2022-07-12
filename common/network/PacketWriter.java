package network;

import io.netty.buffer.ByteBuf;

/**
 * @author Nos
 */
public class PacketWriter
{
	private final ByteBuf _buf;
	
	public PacketWriter(ByteBuf buf)
	{
		_buf = buf;
	}
	
	/**
	 * Gets the writable bytes.
	 * @return the writable bytes
	 */
	public int getWritableBytes()
	{
		return _buf.writableBytes();
	}
	
	/**
	 * Writes a byte.
	 * @param value the byte (The 24 high-order bits are ignored)
	 */
	public void writeC(int value)
	{
		_buf.writeByte(value);
	}
	
	/**
	 * Writes a short.
	 * @param value the short (The 16 high-order bits are ignored)
	 */
	public void writeH(int value)
	{
		_buf.writeShortLE(value);
	}
	
	/**
	 * Writes an integer.
	 * @param value the integer
	 */
	public void writeD(int value)
	{
		_buf.writeIntLE(value);
	}
	
	/**
	 * Writes a long.
	 * @param value the long
	 */
	public void writeQ(long value)
	{
		_buf.writeLongLE(value);
	}
	
	/**
	 * Writes a float.
	 * @param value the float
	 */
	public void writeE(float value)
	{
		_buf.writeIntLE(Float.floatToIntBits(value));
	}
	
	/**
	 * Writes a double.
	 * @param value the double
	 */
	public void writeF(double value)
	{
		_buf.writeLongLE(Double.doubleToLongBits(value));
	}
	
	/**
	 * Writes a string.
	 * @param value the string
	 */
	public void writeS(String value)
	{
		if (value != null)
		{
			for (int i = 0; i < value.length(); i++)
			{
				_buf.writeChar(Character.reverseBytes(value.charAt(i)));
			}
		}
		
		_buf.writeChar(0);
	}
	
	/**
	 * Writes a string with fixed length specified as [short length, char[length] data].
	 * @param value the string
	 */
	public void writeString(String value)
	{
		if (value != null)
		{
			_buf.writeShortLE(value.length());
			for (int i = 0; i < value.length(); i++)
			{
				_buf.writeChar(Character.reverseBytes(value.charAt(i)));
			}
		}
		else
		{
			_buf.writeShort(0);
		}
	}
	
	/**
	 * Writes a byte array.
	 * @param bytes the byte array
	 */
	public void writeB(byte[] bytes)
	{
		_buf.writeBytes(bytes);
	}
}
