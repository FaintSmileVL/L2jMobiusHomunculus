package org.l2jmobius.gameserver.model.actor.status;

import org.l2jmobius.gameserver.ai.CtrlEvent;
import org.l2jmobius.gameserver.model.actor.Creature;
import org.l2jmobius.gameserver.model.actor.instance.PetInstance;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.SystemMessage;

public class PetStatus extends SummonStatus
{
	private int _currentFed = 0; // Current Fed of the PetInstance
	
	public PetStatus(PetInstance activeChar)
	{
		super(activeChar);
	}
	
	@Override
	public void reduceHp(double value, Creature attacker)
	{
		reduceHp(value, attacker, true, false, false);
	}
	
	@Override
	public void reduceHp(double value, Creature attacker, boolean awake, boolean isDOT, boolean isHpConsumption)
	{
		if (getActiveChar().isDead())
		{
			return;
		}
		
		super.reduceHp(value, attacker, awake, isDOT, isHpConsumption);
		
		if (attacker != null)
		{
			if (!isDOT && (getActiveChar().getOwner() != null))
			{
				final SystemMessage sm = new SystemMessage(SystemMessageId.YOUR_PET_RECEIVED_S2_DAMAGE_BY_C1);
				sm.addString(attacker.getName());
				sm.addInt((int) value);
				getActiveChar().sendPacket(sm);
			}
			getActiveChar().getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, attacker);
		}
	}
	
	public int getCurrentFed()
	{
		return _currentFed;
	}
	
	public void setCurrentFed(int value)
	{
		_currentFed = value;
	}
	
	@Override
	public PetInstance getActiveChar()
	{
		return (PetInstance) super.getActiveChar();
	}
}
