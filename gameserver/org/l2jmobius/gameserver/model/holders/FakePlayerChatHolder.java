package org.l2jmobius.gameserver.model.holders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mobius
 */
public class FakePlayerChatHolder
{
	private final String _fpcName;
	private final String _searchMethod;
	private final List<String> _searchText;
	private final List<String> _answers;
	
	public FakePlayerChatHolder(String fpcName, String searchMethod, String searchText, String answers)
	{
		_fpcName = fpcName;
		_searchMethod = searchMethod;
		_searchText = new ArrayList<>(Arrays.asList(searchText.split(";")));
		_answers = new ArrayList<>(Arrays.asList(answers.split(";")));
	}
	
	public String getFpcName()
	{
		return _fpcName;
	}
	
	public String getSearchMethod()
	{
		return _searchMethod;
	}
	
	public List<String> getSearchText()
	{
		return _searchText;
	}
	
	public List<String> getAnswers()
	{
		return _answers;
	}
}
