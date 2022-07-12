package org.l2jmobius.gameserver.enums;

/**
 * @author Zealar
 */
public enum StatFunction
{
	ADD("Add", 30),
	DIV("Div", 20),
	ENCHANT("Enchant", 0),
	ENCHANTHP("EnchantHp", 40),
	MUL("Mul", 20),
	SET("Set", 0),
	SUB("Sub", 30);
	
	String name;
	int order;
	
	StatFunction(String name, int order)
	{
		this.name = name;
		this.order = order;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getOrder()
	{
		return order;
	}
}
