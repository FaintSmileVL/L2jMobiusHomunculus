package org.l2jmobius.gameserver.model.quest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.l2jmobius.gameserver.model.KeyValuePair;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;

/**
 * @author UnAfraid
 */
public class QuestCondition
{
	private final Predicate<PlayerInstance> _condition;
	private Map<Integer, String> _perNpcDialog;
	private final String _html;
	
	public QuestCondition(Predicate<PlayerInstance> cond, String html)
	{
		_condition = cond;
		_html = html;
	}
	
	@SafeVarargs
	public QuestCondition(Predicate<PlayerInstance> cond, KeyValuePair<Integer, String>... pairs)
	{
		_condition = cond;
		_html = null;
		_perNpcDialog = new HashMap<>();
		Stream.of(pairs).forEach(pair -> _perNpcDialog.put(pair.getKey(), pair.getValue()));
	}
	
	public boolean test(PlayerInstance player)
	{
		return _condition.test(player);
	}
	
	public String getHtml(Npc npc)
	{
		return _perNpcDialog != null ? _perNpcDialog.get(npc.getId()) : _html;
	}
}
