package org.l2jmobius.gameserver.util;

/**
 * @author UnAfraid
 */
public class MathUtil
{
	public static byte add(byte oldValue, byte value)
	{
		return (byte) (oldValue + value);
	}
	
	public static short add(short oldValue, short value)
	{
		return (short) (oldValue + value);
	}
	
	public static int add(int oldValue, int value)
	{
		return oldValue + value;
	}
	
	public static double add(double oldValue, double value)
	{
		return oldValue + value;
	}
	
	public static byte mul(byte oldValue, byte value)
	{
		return (byte) (oldValue * value);
	}
	
	public static short mul(short oldValue, short value)
	{
		return (short) (oldValue * value);
	}
	
	public static int mul(int oldValue, int value)
	{
		return oldValue * value;
	}
	
	public static double mul(double oldValue, double value)
	{
		return oldValue * value;
	}
	
	public static byte div(byte oldValue, byte value)
	{
		return (byte) (oldValue / value);
	}
	
	public static short div(short oldValue, short value)
	{
		return (short) (oldValue / value);
	}
	
	public static int div(int oldValue, int value)
	{
		return oldValue / value;
	}
	
	public static double div(double oldValue, double value)
	{
		return oldValue / value;
	}
	
	/**
	 * @param numToTest : The number to test.
	 * @param min : The minimum limit.
	 * @param max : The maximum limit.
	 * @return the number or one of the limit (mininum / maximum).
	 */
	public static int limit(int numToTest, int min, int max)
	{
		return (numToTest > max) ? max : ((numToTest < min) ? min : numToTest);
	}
}
