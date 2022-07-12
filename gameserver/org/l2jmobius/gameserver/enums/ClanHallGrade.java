package org.l2jmobius.gameserver.enums;

/**
 * @author St3eT
 */
public enum ClanHallGrade
{
	GRADE_S(50),
	GRADE_A(40),
	GRADE_B(30),
	GRADE_C(20),
	GRADE_D(10),
	GRADE_NONE(0);
	
	private final int _gradeValue;
	
	private ClanHallGrade(int gradeValue)
	{
		_gradeValue = gradeValue;
	}
	
	/**
	 * @return the grade value.
	 */
	public int getGradeValue()
	{
		return _gradeValue;
	}
}