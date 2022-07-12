package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.gameserver.ai.ControllableMobAI;
import org.l2jmobius.gameserver.ai.CreatureAI;
import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;

/**
 * @author littlecrow
 */
public class ControllableMobInstance extends MonsterInstance
{
	private boolean _isInvul;
	
	@Override
	public boolean isAggressive()
	{
		return true;
	}
	
	@Override
	public int getAggroRange()
	{
		// force mobs to be aggro
		return 500;
	}
	
	public ControllableMobInstance(NpcTemplate template)
	{
		super(template);
		setInstanceType(InstanceType.ControllableMobInstance);
	}
	
	@Override
	protected CreatureAI initAI()
	{
		return new ControllableMobAI(this);
	}
	
	@Override
	public void detachAI()
	{
		// do nothing, AI of controllable mobs can't be detached automatically
	}
	
	@Override
	public boolean isInvul()
	{
		return _isInvul;
	}
	
	@Override
	public void setInvul(boolean isInvul)
	{
		_isInvul = isInvul;
	}
	
	@Override
	public boolean doDie(Creature killer)
	{
		if (!super.doDie(killer))
		{
			return false;
		}
		
		setAI(null);
		return true;
	}
}